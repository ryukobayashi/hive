/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableMap;
import org.apache.calcite.adapter.druid.DruidQuery;
import org.apache.calcite.adapter.druid.DruidSchema;
import org.apache.calcite.adapter.druid.DruidTable;
import org.apache.calcite.interpreter.BindableConvention;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptMaterialization;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeField;
import org.apache.calcite.rel.type.RelDataTypeImpl;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.hadoop.hive.conf.Constants;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.metastore.DefaultMetaStoreFilterHookImpl;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.DriverUtils;
import org.apache.hadoop.hive.ql.exec.ColumnInfo;
import org.apache.hadoop.hive.ql.log.PerfLogger;
import org.apache.hadoop.hive.ql.optimizer.calcite.HiveTypeSystemImpl;
import org.apache.hadoop.hive.ql.optimizer.calcite.RelOptHiveTable;
import org.apache.hadoop.hive.ql.optimizer.calcite.reloperators.HiveRelNode;
import org.apache.hadoop.hive.ql.optimizer.calcite.reloperators.HiveTableScan;
import org.apache.hadoop.hive.ql.optimizer.calcite.rules.views.HiveMaterializedViewUtils;
import org.apache.hadoop.hive.ql.optimizer.calcite.rules.views.IncrementalRebuildMode;
import org.apache.hadoop.hive.ql.optimizer.calcite.rules.views.MaterializedViewIncrementalRewritingRelVisitor;
import org.apache.hadoop.hive.ql.optimizer.calcite.translator.TypeConverter;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.CBOPlan;
import org.apache.hadoop.hive.ql.parse.CalcitePlanner;
import org.apache.hadoop.hive.ql.parse.ParseUtils;
import org.apache.hadoop.hive.ql.parse.QueryTables;
import org.apache.hadoop.hive.ql.parse.RowResolver;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.google.common.collect.ImmutableList;

import static java.util.stream.Collectors.toList;
import static org.apache.hadoop.hive.ql.metadata.RewriteAlgorithm.ALL;

/**
 * Registry for materialized views. The goal of this cache is to avoid parsing and creating
 * logical plans for the materialized views at query runtime. When a query arrives, we will
 * just need to consult this cache and extract the logical plans for the views (which had
 * already been parsed) from it. This cache lives in HS2.
 */
public final class HiveMaterializedViewsRegistry {

  private static final Logger LOG = LoggerFactory.getLogger(HiveMaterializedViewsRegistry.class);
  private static final String CLASS_NAME = HiveMaterializedViewsRegistry.class.getName();

  /* Singleton */
  private static final HiveMaterializedViewsRegistry SINGLETON = new HiveMaterializedViewsRegistry();

  private final MaterializedViewsCache materializedViewsCache = new MaterializedViewsCache();

  /* Whether the cache has been initialized or not. */
  private final AtomicBoolean initialized = new AtomicBoolean(false);

  private HiveMaterializedViewsRegistry() {
  }

  /**
   * Get instance of HiveMaterializedViewsRegistry.
   *
   * @return the singleton
   */
  public static HiveMaterializedViewsRegistry get() {
    return SINGLETON;
  }

  /**
   * Initialize the registry for the given database. It will extract the materialized views
   * that are enabled for rewriting from the metastore for the current user, parse them,
   * and register them in this cache.
   *
   * The loading process runs on the background; the method returns in the moment that the
   * runnable task is created, thus the views will still not be loaded in the cache when
   * it returns.
   */
  public void init() {
    try {
      // Create a new conf object to bypass metastore authorization, as we need to
      // retrieve all materialized views from all databases
      HiveConf conf = new HiveConf();
      conf.set(MetastoreConf.ConfVars.FILTER_HOOK.getVarname(),
          DefaultMetaStoreFilterHookImpl.class.getName());
      init(Hive.get(conf));
    } catch (HiveException e) {
      LOG.error("Problem connecting to the metastore when initializing the view registry", e);
    }
  }

  public void init(Hive db) {
    final boolean dummy = db.getConf().get(HiveConf.ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_IMPL.varname)
        .equals("DUMMY");
    if (dummy) {
      // Dummy registry does not cache information and forwards all requests to metastore
      initialized.set(true);
      LOG.info("Using dummy materialized views registry");
    } else {
      // We initialize the cache
      long period = HiveConf.getTimeVar(db.getConf(), ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_REFRESH, TimeUnit.SECONDS);
      ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor(
          new ThreadFactoryBuilder()
              .setDaemon(true)
              .setNameFormat("HiveMaterializedViewsRegistry-%d")
              .build());
      pool.scheduleAtFixedRate(new Loader(db), 0, period, TimeUnit.SECONDS);
    }
  }

  private class Loader implements Runnable {
    private final Hive db;

    private Loader(Hive db) {
      this.db = db;
    }

    @Override
    public void run() {
      PerfLogger perfLogger = SessionState.getPerfLogger();
      try {
        DriverUtils.setUpAndStartSessionState(db.getConf());
        perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.MATERIALIZED_VIEWS_REGISTRY_REFRESH);
        if (initialized.get()) {
          for (Table mvTable : db.getAllMaterializedViewObjectsForRewriting()) {
            RelOptMaterialization existingMV = getRewritingMaterializedView(
                    mvTable.getDbName(), mvTable.getTableName(), ALL);
            if (existingMV != null) {
              // We replace if the existing MV is not newer
              Table existingMVTable = HiveMaterializedViewUtils.extractTable(existingMV);
              if (existingMVTable.getCreateTime() < mvTable.getCreateTime() ||
                  (existingMVTable.getCreateTime() == mvTable.getCreateTime() &&
                      existingMVTable.getMVMetadata().getMaterializationTime() <= mvTable.getMVMetadata().getMaterializationTime())) {
                refreshMaterializedView(db.getConf(), existingMVTable, mvTable);
              }
            } else {
              // Simply replace if it still does not exist
              refreshMaterializedView(db.getConf(), null, mvTable);
            }
          }
          LOG.info("Materialized views registry has been refreshed");
        } else {
          for (Table mvTable : db.getAllMaterializedViewObjectsForRewriting()) {
            refreshMaterializedView(db.getConf(), null, mvTable);
          }
          initialized.set(true);
          LOG.info("Materialized views registry has been initialized");
        }
      } catch (HiveException e) {
        if (initialized.get()) {
          LOG.error("Problem connecting to the metastore when refreshing the view registry", e);
        } else {
          LOG.error("Problem connecting to the metastore when initializing the view registry", e);
        }
      }
      perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.MATERIALIZED_VIEWS_REGISTRY_REFRESH);
    }
  }

  public boolean isInitialized() {
    return initialized.get();
  }

  /**
   * Parses and creates a materialization.
   */
  public HiveRelOptMaterialization createMaterialization(HiveConf conf, Table materializedViewTable) {
    // First we parse the view query and create the materialization object
    final String viewQuery = materializedViewTable.getViewExpandedText();
    final RelNode viewScan = createMaterializedViewScan(conf, materializedViewTable);
    if (viewScan == null) {
      LOG.warn("Materialized view " + materializedViewTable.getCompleteName() +
          " ignored; error creating view replacement");
      return null;
    }
    final CBOPlan plan;
    try {
      plan = ParseUtils.parseQuery(createContext(conf), viewQuery);
    } catch (Exception e) {
      LOG.warn("Materialized view " + materializedViewTable.getCompleteName() +
          " ignored; error parsing original query; " + e);
      return null;
    }

    return new HiveRelOptMaterialization(viewScan, plan.getPlan(),
            null, viewScan.getTable().getQualifiedName(), plan.getSupportedRewriteAlgorithms(),
            determineIncrementalRebuildMode(plan.getPlan()), plan.getAst());
  }

  private IncrementalRebuildMode determineIncrementalRebuildMode(RelNode definitionPlan) {
    return new MaterializedViewIncrementalRewritingRelVisitor().go(definitionPlan).getIncrementalRebuildMode();
  }

  /**
   * Adds a newly created materialized view to the cache.
   */
  public void createMaterializedView(HiveConf conf, Table materializedViewTable) {
    final boolean cache = !conf.get(HiveConf.ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_IMPL.varname)
        .equals("DUMMY");
    if (!cache) {
      // Nothing to do, bail out
      return;
    }

    // Bail out if it is not enabled for rewriting
    if (!materializedViewTable.isRewriteEnabled()) {
      LOG.debug("Materialized view " + materializedViewTable.getCompleteName() +
          " ignored; it is not rewrite enabled");
      return;
    }

    HiveRelOptMaterialization materialization = createMaterialization(conf, materializedViewTable);
    if (materialization == null || materialization.getScope().isEmpty()) {
      return;
    }

    materializedViewsCache.putIfAbsent(materializedViewTable, materialization);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Created materialized view for rewriting: " + materializedViewTable.getFullyQualifiedName());
    }
  }

  /**
   * Update the materialized view in the registry (if materialized view exists).
   */
  public void refreshMaterializedView(HiveConf conf, Table materializedViewTable) {
    RelOptMaterialization cached = materializedViewsCache.get(
        materializedViewTable.getDbName(), materializedViewTable.getTableName());
    if (cached == null) {
      return;
    }
    Table cachedTable = HiveMaterializedViewUtils.extractTable(cached);
    refreshMaterializedView(conf, cachedTable, materializedViewTable);
  }

  /**
   * Update the materialized view in the registry (if existing materialized view matches).
   */
  public void refreshMaterializedView(HiveConf conf, Table oldMaterializedViewTable, Table materializedViewTable) {
    final boolean cache = !conf.get(HiveConf.ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_IMPL.varname)
        .equals("DUMMY");
    if (!cache) {
      // Nothing to do, bail out
      return;
    }

    // Bail out if it is not enabled for rewriting
    if (!materializedViewTable.isRewriteEnabled()) {
      dropMaterializedView(oldMaterializedViewTable);
      LOG.debug("Materialized view " + materializedViewTable.getCompleteName() +
          " dropped; it is not rewrite enabled");
      return;
    }

    final HiveRelOptMaterialization newMaterialization = createMaterialization(conf, materializedViewTable);
    if (newMaterialization == null) {
      return;
    }
    materializedViewsCache.refresh(oldMaterializedViewTable, materializedViewTable, newMaterialization);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Materialized view refreshed: " + materializedViewTable.getFullyQualifiedName());
    }
  }

  /**
   * Removes the materialized view from the cache (based on table object equality), if exists.
   */
  public void dropMaterializedView(Table materializedViewTable) {
    materializedViewsCache.remove(materializedViewTable);
  }

  /**
   * Removes the materialized view from the cache (based on qualified name), if exists.
   */
  public void dropMaterializedView(String dbName, String tableName) {
    materializedViewsCache.remove(dbName, tableName);
  }

  /**
   * Returns all the materialized views enabled for Calcite based rewriting in the cache.
   *
   * @return the collection of materialized views, or the empty collection if none
   */
  List<HiveRelOptMaterialization> getRewritingMaterializedViews() {
    return materializedViewsCache.values().stream()
            .filter(materialization -> materialization.getScope().contains(RewriteAlgorithm.CALCITE))
            .collect(toList());
  }

  /**
   * Returns the materialized views in the cache for the given database.
   *
   * @return the collection of materialized views, or the empty collection if none
   */
  public HiveRelOptMaterialization getRewritingMaterializedView(String dbName, String viewName,
                                                         EnumSet<RewriteAlgorithm> scope) {
    HiveRelOptMaterialization materialization = materializedViewsCache.get(dbName, viewName);
    if (materialization == null) {
      return null;
    }
    if (!materialization.isSupported(scope)) {
      return null;
    }
    return materialization;
  }

  public List<HiveRelOptMaterialization> getRewritingMaterializedViews(ASTNode ast) {
    return materializedViewsCache.get(ast);
  }

  private Context createContext(HiveConf conf) {
    Context ctx = new Context(conf);
    ctx.setIsLoadingMaterializedView(true);
    ctx.setHDFSCleanup(true);
    return ctx;
  }

  public boolean isEmpty() {
    return materializedViewsCache.isEmpty();
  }


  private static RelNode createMaterializedViewScan(HiveConf conf, Table viewTable) {
    // 0. Recreate cluster
    final RelOptPlanner planner = CalcitePlanner.createPlanner(conf);
    final RexBuilder rexBuilder = new RexBuilder(
        new JavaTypeFactoryImpl(
            new HiveTypeSystemImpl()));
    final RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);

    // 1. Create column schema
    final RowResolver rr = new RowResolver();
    // 1.1 Add Column info for non partion cols (Object Inspector fields)
    StructObjectInspector rowObjectInspector;
    try {
      rowObjectInspector = (StructObjectInspector) viewTable.getDeserializer()
          .getObjectInspector();
    } catch (SerDeException e) {
      // Bail out
      return null;
    }
    List<? extends StructField> fields = rowObjectInspector.getAllStructFieldRefs();
    ColumnInfo colInfo;
    String colName;
    ArrayList<ColumnInfo> cInfoLst = new ArrayList<>();
    for (StructField structField : fields) {
      colName = structField.getFieldName();
      colInfo = new ColumnInfo(
          structField.getFieldName(),
          TypeInfoUtils.getTypeInfoFromObjectInspector(structField.getFieldObjectInspector()),
          null, false);
      rr.put(null, colName, colInfo);
      cInfoLst.add(colInfo);
    }
    ArrayList<ColumnInfo> nonPartitionColumns = new ArrayList<ColumnInfo>(cInfoLst);

    // 1.2 Add column info corresponding to partition columns
    ArrayList<ColumnInfo> partitionColumns = new ArrayList<ColumnInfo>();
    for (FieldSchema part_col : viewTable.getPartCols()) {
      colName = part_col.getName();
      colInfo = new ColumnInfo(colName,
          TypeInfoFactory.getPrimitiveTypeInfo(part_col.getType()), null, true);
      rr.put(null, colName, colInfo);
      cInfoLst.add(colInfo);
      partitionColumns.add(colInfo);
    }

    // 1.3 Build row type from field <type, name>
    RelDataType rowType = TypeConverter.getType(cluster, rr, null);

    // 2. Build RelOptAbstractTable
    List<String> fullyQualifiedTabName = new ArrayList<>();
    if (viewTable.getDbName() != null && !viewTable.getDbName().isEmpty()) {
      fullyQualifiedTabName.add(viewTable.getDbName());
    }
    fullyQualifiedTabName.add(viewTable.getTableName());

    RelNode tableRel;

    // 3. Build operator
    if (obtainTableType(viewTable) == TableType.DRUID) {
      // Build Druid query
      String address = HiveConf.getVar(conf,
          HiveConf.ConfVars.HIVE_DRUID_BROKER_DEFAULT_ADDRESS);
      String dataSource = viewTable.getParameters().get(Constants.DRUID_DATA_SOURCE);
      Set<String> metrics = new HashSet<>();
      List<RelDataType> druidColTypes = new ArrayList<>();
      List<String> druidColNames = new ArrayList<>();
      //@NOTE this code is very similar to the code at org/apache/hadoop/hive/ql/parse/CalcitePlanner.java:2362
      //@TODO it will be nice to refactor it
      RelDataTypeFactory dtFactory = cluster.getRexBuilder().getTypeFactory();
      for (RelDataTypeField field : rowType.getFieldList()) {
        if (DruidTable.DEFAULT_TIMESTAMP_COLUMN.equals(field.getName())) {
          // Druid's time column is always not null.
          druidColTypes.add(dtFactory.createTypeWithNullability(field.getType(), false));
        } else {
          druidColTypes.add(field.getType());
        }
        druidColNames.add(field.getName());
        if (field.getName().equals(DruidTable.DEFAULT_TIMESTAMP_COLUMN)) {
          // timestamp
          continue;
        }
        if (field.getType().getSqlTypeName() == SqlTypeName.VARCHAR) {
          // dimension
          continue;
        }
        metrics.add(field.getName());
      }

      List<Interval> intervals = Collections.singletonList(DruidTable.DEFAULT_INTERVAL);
      rowType = dtFactory.createStructType(druidColTypes, druidColNames);
      // We can pass null for Hive object because it is only used to retrieve tables
      // if constraints on a table object are existing, but constraints cannot be defined
      // for materialized views.
      RelOptHiveTable optTable = new RelOptHiveTable(null, cluster.getTypeFactory(), fullyQualifiedTabName,
          rowType, viewTable, nonPartitionColumns, partitionColumns, new ArrayList<>(),
          conf, new QueryTables(true), new HashMap<>(), new HashMap<>(), new AtomicInteger());
      DruidTable druidTable = new DruidTable(new DruidSchema(address, address, false),
          dataSource, RelDataTypeImpl.proto(rowType), metrics, DruidTable.DEFAULT_TIMESTAMP_COLUMN,
          intervals, null, null);
      final TableScan scan = new HiveTableScan(cluster, cluster.traitSetOf(HiveRelNode.CONVENTION),
          optTable, viewTable.getTableName(), null, false, false);
      tableRel = DruidQuery.create(cluster, cluster.traitSetOf(BindableConvention.INSTANCE),
          optTable, druidTable, ImmutableList.<RelNode>of(scan), ImmutableMap.of());
    } else {
      // Build Hive Table Scan Rel.
      // We can pass null for Hive object because it is only used to retrieve tables
      // if constraints on a table object are existing, but constraints cannot be defined
      // for materialized views.
      RelOptHiveTable optTable = new RelOptHiveTable(null, cluster.getTypeFactory(), fullyQualifiedTabName,
          rowType, viewTable, nonPartitionColumns, partitionColumns, new ArrayList<>(),
          conf, new QueryTables(true), new HashMap<>(), new HashMap<>(), new AtomicInteger());
      tableRel = new HiveTableScan(cluster, cluster.traitSetOf(HiveRelNode.CONVENTION), optTable,
          viewTable.getTableName(), null, false, false);
    }

    return tableRel;
  }

  private static TableType obtainTableType(Table tabMetaData) {
    if (tabMetaData.getStorageHandler() != null) {
      final String storageHandlerStr = tabMetaData.getStorageHandler().toString();
      if (storageHandlerStr.equals(Constants.DRUID_HIVE_STORAGE_HANDLER_ID)) {
        return TableType.DRUID;
      }

      if (storageHandlerStr.equals(Constants.JDBC_HIVE_STORAGE_HANDLER_ID)) {
        return TableType.JDBC;
      }

    }

    return TableType.NATIVE;
  }

  //@TODO this seems to be the same as org.apache.hadoop.hive.ql.parse.CalcitePlanner.TableType.DRUID do we really need both
  private enum TableType {
    DRUID,
    NATIVE,
    JDBC
  }

}
