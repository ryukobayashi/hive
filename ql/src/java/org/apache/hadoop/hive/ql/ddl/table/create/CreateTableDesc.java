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

package org.apache.hadoop.hive.ql.ddl.table.create;


import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.StatsSetupConst;
import org.apache.hadoop.hive.common.TableName;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.metastore.api.ColumnStatistics;
import org.apache.hadoop.hive.metastore.api.ColumnStatisticsDesc;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.ObjectDictionary;
import org.apache.hadoop.hive.metastore.api.Order;
import org.apache.hadoop.hive.metastore.api.hive_metastoreConstants;
import org.apache.hadoop.hive.metastore.api.SQLCheckConstraint;
import org.apache.hadoop.hive.metastore.api.SQLDefaultConstraint;
import org.apache.hadoop.hive.metastore.api.SQLForeignKey;
import org.apache.hadoop.hive.metastore.api.SQLNotNullConstraint;
import org.apache.hadoop.hive.metastore.api.SQLPrimaryKey;
import org.apache.hadoop.hive.metastore.api.SQLUniqueConstraint;
import org.apache.hadoop.hive.metastore.utils.MetaStoreUtils;
import org.apache.hadoop.hive.ql.ErrorMsg;
import org.apache.hadoop.hive.ql.ddl.DDLDescWithTableProperties;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.ddl.DDLUtils;
import org.apache.hadoop.hive.ql.io.HiveFileFormatUtils;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.HiveStorageHandler;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer;
import org.apache.hadoop.hive.ql.parse.ParseUtils;
import org.apache.hadoop.hive.ql.parse.ReplicationSpec;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.Explain;
import org.apache.hadoop.hive.ql.plan.PlanUtils;
import org.apache.hadoop.hive.ql.plan.ValidationUtility;
import org.apache.hadoop.hive.ql.plan.Explain.Level;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.mapred.OutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.hadoop.hive.ql.ddl.DDLUtils.setColumnsAndStorePartitionTransformSpecOfTable;

/**
 * DDL task description for CREATE TABLE commands.
 */
@Explain(displayName = "Create Table", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
public class CreateTableDesc extends DDLDescWithTableProperties implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(CreateTableDesc.class);

  TableName tableName;
  boolean isExternal;
  List<String> bucketCols;
  List<Order> sortCols;
  int numBuckets;
  String fieldDelim;
  String fieldEscape;
  String collItemDelim;
  String mapKeyDelim;
  String lineDelim;
  String nullFormat;
  List<String> skewedColNames;
  List<List<String>> skewedColValues;
  boolean isStoredAsSubDirectories = false;
  private boolean replaceMode = false;
  private ReplicationSpec replicationSpec = null;
  private boolean isCTAS = false;
  List<SQLPrimaryKey> primaryKeys;
  List<SQLForeignKey> foreignKeys;
  List<SQLUniqueConstraint> uniqueConstraints;
  List<SQLNotNullConstraint> notNullConstraints;
  List<SQLDefaultConstraint> defaultConstraints;
  List<SQLCheckConstraint> checkConstraints;
  private ColumnStatistics colStats;  // For the sake of replication
  private Long replWriteId; // to be used by repl task to get the txn and valid write id list
  private String likeFile = null;
  private String likeFileFormat = null;

  public CreateTableDesc() {
  }

  public CreateTableDesc(TableName tableName, boolean isExternal, boolean isTemporary,
      List<FieldSchema> cols, List<FieldSchema> partCols,
      List<String> bucketCols, List<Order> sortCols, int numBuckets,
      String fieldDelim, String fieldEscape, String collItemDelim,
      String mapKeyDelim, String lineDelim, String comment, String inputFormat,
      String outputFormat, String location, String serName,
      String storageHandler,
      Map<String, String> serdeProps,
      Map<String, String> tblProps,
      boolean ifNotExists, List<String> skewedColNames, List<List<String>> skewedColValues,
      List<SQLPrimaryKey> primaryKeys, List<SQLForeignKey> foreignKeys,
      List<SQLUniqueConstraint> uniqueConstraints, List<SQLNotNullConstraint> notNullConstraints,
      List<SQLDefaultConstraint> defaultConstraints, List<SQLCheckConstraint> checkConstraints,
      ColumnStatistics colStats, long writeId) {

    this(tableName, isExternal, isTemporary, cols, partCols,
        bucketCols, sortCols, numBuckets, fieldDelim, fieldEscape,
        collItemDelim, mapKeyDelim, lineDelim, comment, inputFormat,
        outputFormat, location, serName, storageHandler, serdeProps,
        tblProps, ifNotExists, skewedColNames, skewedColValues,
        primaryKeys, foreignKeys, uniqueConstraints, notNullConstraints, defaultConstraints, checkConstraints);

    this.colStats = colStats;
    this.replWriteId = writeId;
  }

  public CreateTableDesc(TableName tableName, boolean isExternal, boolean isTemporary,
      List<FieldSchema> cols, List<String> partColNames,
      List<String> bucketCols, List<Order> sortCols, int numBuckets,
      String fieldDelim, String fieldEscape, String collItemDelim,
      String mapKeyDelim, String lineDelim, String comment, String inputFormat,
      String outputFormat, String location, String serName,
      String storageHandler,
      Map<String, String> serdeProps,
      Map<String, String> tblProps,
      boolean ifNotExists, List<String> skewedColNames, List<List<String>> skewedColValues,
      boolean isCTAS, List<SQLPrimaryKey> primaryKeys, List<SQLForeignKey> foreignKeys,
      List<SQLUniqueConstraint> uniqueConstraints, List<SQLNotNullConstraint> notNullConstraints,
      List<SQLDefaultConstraint> defaultConstraints, List<SQLCheckConstraint> checkConstraints) {
    this(tableName, isExternal, isTemporary, cols, new ArrayList<>(),
        bucketCols, sortCols, numBuckets, fieldDelim, fieldEscape,
        collItemDelim, mapKeyDelim, lineDelim, comment, inputFormat,
        outputFormat, location, serName, storageHandler, serdeProps,
        tblProps, ifNotExists, skewedColNames, skewedColValues,
        primaryKeys, foreignKeys, uniqueConstraints, notNullConstraints, defaultConstraints, checkConstraints,
       null, -1);
    this.partColNames = partColNames;
    this.isCTAS = isCTAS;
  }

  public CreateTableDesc(TableName tableName, boolean isExternal, boolean isTemporary,
      List<FieldSchema> cols, List<FieldSchema> partCols,
      List<String> bucketCols, List<Order> sortCols, int numBuckets,
      String fieldDelim, String fieldEscape, String collItemDelim,
      String mapKeyDelim, String lineDelim, String comment, String inputFormat,
      String outputFormat, String location, String serName,
      String storageHandler,
      Map<String, String> serdeProps,
      Map<String, String> tblProps,
      boolean ifNotExists, List<String> skewedColNames, List<List<String>> skewedColValues,
      List<SQLPrimaryKey> primaryKeys, List<SQLForeignKey> foreignKeys,
      List<SQLUniqueConstraint> uniqueConstraints, List<SQLNotNullConstraint> notNullConstraints,
      List<SQLDefaultConstraint> defaultConstraints, List<SQLCheckConstraint> checkConstraints) {
    super(cols, partCols, comment, inputFormat, outputFormat, location, serName, storageHandler, 
      serdeProps, tblProps, ifNotExists);
    
    this.tableName = tableName;
    this.isExternal = isExternal;
    this.isTemporary = isTemporary;
    this.bucketCols = new ArrayList<>(bucketCols);
    this.sortCols = new ArrayList<>(sortCols);
    this.collItemDelim = collItemDelim;
    this.fieldDelim = fieldDelim;
    this.fieldEscape = fieldEscape;
    this.lineDelim = lineDelim;
    this.mapKeyDelim = mapKeyDelim;
    this.numBuckets = numBuckets;
    this.skewedColNames = copyList(skewedColNames);
    this.skewedColValues = copyList(skewedColValues);
    this.primaryKeys = copyList(primaryKeys);
    this.foreignKeys = copyList(foreignKeys);
    this.uniqueConstraints = copyList(uniqueConstraints);
    this.notNullConstraints = copyList(notNullConstraints);
    this.defaultConstraints = copyList(defaultConstraints);
    this.checkConstraints= copyList(checkConstraints);
  }

  private static <T> List<T> copyList(List<T> copy) {
    return copy == null ? null : new ArrayList<>(copy);
  }

  public void setLikeFile(String likeFile) {
    this.likeFile = likeFile;
  }

  public void setLikeFileFormat(String likeFileFormat) {
    this.likeFileFormat = likeFileFormat;
  }

  public String getLikeFile() {
    return likeFile;
  }

  public String getLikeFileFormat() {
    return likeFileFormat;
  }

  @Explain(displayName = "name", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getDbTableName() {
    return tableName.getNotEmptyDbTable();
  }

  public TableName getFullTableName() {
    return tableName;
  }

  public String getDatabaseName(){
    return tableName.getDb();
  }

  public void setTableName(TableName tableName) {
    this.tableName = tableName;
  }

  public List<SQLPrimaryKey> getPrimaryKeys() {
    return primaryKeys;
  }

  public void setPrimaryKeys(ArrayList<SQLPrimaryKey> primaryKeys) {
    this.primaryKeys = primaryKeys;
  }

  public List<SQLForeignKey> getForeignKeys() {
    return foreignKeys;
  }

  public void setForeignKeys(ArrayList<SQLForeignKey> foreignKeys) {
    this.foreignKeys = foreignKeys;
  }

  public List<SQLUniqueConstraint> getUniqueConstraints() {
    return uniqueConstraints;
  }

  public List<SQLNotNullConstraint> getNotNullConstraints() {
    return notNullConstraints;
  }

  public List<SQLDefaultConstraint> getDefaultConstraints() {
    return defaultConstraints;
  }

  public List<SQLCheckConstraint> getCheckConstraints() {
    return checkConstraints;
  }

  @Explain(displayName = "bucket columns")
  public List<String> getBucketCols() {
    return bucketCols;
  }

  public void setBucketCols(ArrayList<String> bucketCols) {
    this.bucketCols = bucketCols;
  }

  @Explain(displayName = "# buckets")
  public Integer getNumBucketsExplain() {
    if (numBuckets == -1) {
      return null;
    } else {
      return numBuckets;
    }
  }

  public int getNumBuckets() {
    return numBuckets;
  }

  public void setNumBuckets(int numBuckets) {
    this.numBuckets = numBuckets;
  }

  @Explain(displayName = "field delimiter")
  public String getFieldDelim() {
    return fieldDelim;
  }

  public void setFieldDelim(String fieldDelim) {
    this.fieldDelim = fieldDelim;
  }

  @Explain(displayName = "field escape")
  public String getFieldEscape() {
    return fieldEscape;
  }

  public void setFieldEscape(String fieldEscape) {
    this.fieldEscape = fieldEscape;
  }

  @Explain(displayName = "collection delimiter")
  public String getCollItemDelim() {
    return collItemDelim;
  }

  public void setCollItemDelim(String collItemDelim) {
    this.collItemDelim = collItemDelim;
  }

  @Explain(displayName = "map key delimiter")
  public String getMapKeyDelim() {
    return mapKeyDelim;
  }

  public void setMapKeyDelim(String mapKeyDelim) {
    this.mapKeyDelim = mapKeyDelim;
  }

  @Explain(displayName = "line delimiter")
  public String getLineDelim() {
    return lineDelim;
  }

  public void setLineDelim(String lineDelim) {
    this.lineDelim = lineDelim;
  }

  @Explain(displayName = "isExternal", displayOnlyOnTrue = true)
  public boolean isExternal() {
    return isExternal;
  }

  public void setExternal(boolean isExternal) {
    this.isExternal = isExternal;
  }

  /**
   * @return the sortCols
   */
  @Explain(displayName = "sort columns")
  public List<Order> getSortCols() {
    return sortCols;
  }

  /**
   * @param sortCols
   *          the sortCols to set
   */
  public void setSortCols(ArrayList<Order> sortCols) {
    this.sortCols = sortCols;
  }

  /**
   * @return the skewedColNames
   */
  public List<String> getSkewedColNames() {
    return skewedColNames;
  }

  /**
   * @param skewedColNames the skewedColNames to set
   */
  public void setSkewedColNames(ArrayList<String> skewedColNames) {
    this.skewedColNames = skewedColNames;
  }

  /**
   * @return the skewedColValues
   */
  public List<List<String>> getSkewedColValues() {
    return skewedColValues;
  }

  /**
   * @param skewedColValues the skewedColValues to set
   */
  public void setSkewedColValues(ArrayList<List<String>> skewedColValues) {
    this.skewedColValues = skewedColValues;
  }

  public void validate(HiveConf conf) throws SemanticException {

    if (CollectionUtils.isEmpty(getCols())) {
      // if the table has no columns and is a HMS backed SerDe - it should have a storage handler OR
      // is a CREATE TABLE LIKE FILE statement.
      if (Table.hasMetastoreBasedSchema(conf, getSerde()) && StringUtils.isEmpty(getStorageHandler())
          && this.getLikeFile() == null) {
        throw new SemanticException(ErrorMsg.INVALID_TBL_DDL_SERDE.getMsg());
      }
      return;
    }

    if (this.getStorageHandler() == null) {
      try {
        Class<?> origin = Class.forName(this.getOutputFormat(), true, Utilities.getSessionSpecifiedClassLoader());
        Class<? extends OutputFormat> replaced = HiveFileFormatUtils.getOutputFormatSubstitute(origin);
        if (!HiveOutputFormat.class.isAssignableFrom(replaced)) {
          throw new SemanticException(ErrorMsg.INVALID_OUTPUT_FORMAT_TYPE.getMsg());
        }
      } catch (ClassNotFoundException e) {
        throw new SemanticException(ErrorMsg.CLASSPATH_ERROR.getMsg(), e);
      }
    }

    List<String> colNames = ParseUtils.validateColumnNameUniqueness(this.getCols());

    assertColumnsMatchSchema(colNames, this.getBucketCols());
    assertColumnsMatchSchema(colNames, Iterables.transform(this.getSortCols(), Order::getCol));

    if (this.getPartCols() != null) {
      // there is no overlap between columns and partitioning columns
      for (FieldSchema fs : this.getPartCols()) {
        String partCol = fs.getName();
        TypeInfo pti = null;
        try {
          pti = TypeInfoFactory.getPrimitiveTypeInfo(fs.getType());
        } catch (Exception err) {
          LOG.error("Failed to get type info", err);
        }
        if (null == pti) {
          throw new SemanticException(ErrorMsg.PARTITION_COLUMN_NON_PRIMITIVE.getMsg() + " Found "
            + partCol + " of type: " + fs.getType());
        }
        for (String name : colNames) {
          String colName = BaseSemanticAnalyzer.unescapeIdentifier(name);
          if (partCol.equalsIgnoreCase(colName)) {
            throw new SemanticException(
              ErrorMsg.COLUMN_REPEATED_IN_PARTITIONING_COLS.getMsg());
          }
        }
      }
    }

    /* Validate skewed information. */
    ValidationUtility.validateSkewedInformation(colNames, this.getSkewedColNames(),
        this.getSkewedColValues());
  }

  private static void assertColumnsMatchSchema(List<String> schema, Iterable<String> colNames) throws SemanticException {
    if (colNames != null) {
      // all columns in cluster and sort are valid columns
      for (String column : colNames) {
        if (schema.stream().noneMatch(column::equalsIgnoreCase)) {
          throw new SemanticException(ErrorMsg.INVALID_COLUMN.getMsg(" '" + column + "'"));
        }
      }
    }
  }

  /**
   * @return the isStoredAsSubDirectories
   */
  public boolean isStoredAsSubDirectories() {
    return isStoredAsSubDirectories;
  }

  /**
   * @param isStoredAsSubDirectories the isStoredAsSubDirectories to set
   */
  public void setStoredAsSubDirectories(boolean isStoredAsSubDirectories) {
    this.isStoredAsSubDirectories = isStoredAsSubDirectories;
  }

  /**
   * @return the nullFormat
   */
  public String getNullFormat() {
    return nullFormat;
  }

  /**
   * Set null format string
   * @param nullFormat
   */
  public void setNullFormat(String nullFormat) {
    this.nullFormat = nullFormat;
  }

  /**
   * @param isTemporary table is Temporary or not.
   */
  public void setTemporary(boolean isTemporary) {
    this.isTemporary = isTemporary;
  }

  /**
   * @param isMaterialization table is a materialization or not.
   */
  public void setMaterialization(boolean isMaterialization) {
    this.isMaterialization = isMaterialization;
  }

  /**
   * @param replaceMode Determine if this CreateTable should behave like a replace-into alter instead
   */
  public void setReplaceMode(boolean replaceMode) {
    this.replaceMode = replaceMode;
  }

  /**
   * @return true if this CreateTable should behave like a replace-into alter instead
   */
  public boolean getReplaceMode() {
    return replaceMode;
  }

  /**
   * @param replicationSpec Sets the replication spec governing this create.
   * This parameter will have meaningful values only for creates happening as a result of a replication.
   */
  public void setReplicationSpec(ReplicationSpec replicationSpec) {
    this.replicationSpec = replicationSpec;
  }

  /**
   * @return what kind of replication scope this drop is running under.
   * This can result in a "CREATE/REPLACE IF NEWER THAN" kind of semantic
   */
  public ReplicationSpec getReplicationSpec() {
    if (replicationSpec == null) {
      this.replicationSpec = new ReplicationSpec();
    }
    return this.replicationSpec;
  }

  public boolean isCTAS() {
    return isCTAS;
  }

  public Table toTable(HiveConf conf) throws HiveException {

    Table tbl = new Table(tableName.getDb(), tableName.getTable());

    if (getTblProps() != null) {
      tbl.getTTable().getParameters().putAll(getTblProps());
    }

    if (getNumBuckets() != -1) {
      tbl.setNumBuckets(getNumBuckets());
    }

    if (getStorageHandler() != null) {
      tbl.setProperty(
              org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE,
              getStorageHandler());
    }
    HiveStorageHandler storageHandler = tbl.getStorageHandler();

    /*
     * If the user didn't specify a SerDe, we use the default.
     */
    String serDeClassName;
    if (getSerde() == null) {
      if (storageHandler == null) {
        serDeClassName = PlanUtils.getDefaultSerDe().getName();
        LOG.info("Default to " + serDeClassName + " for table " + tableName);
      } else {
        serDeClassName = storageHandler.getSerDeClass().getName();
        LOG.info("Use StorageHandler-supplied " + serDeClassName
                + " for table " + tableName);
      }
    } else {
      // let's validate that the serde exists
      serDeClassName = getSerde();
      DDLUtils.validateSerDe(serDeClassName, conf);
    }
    tbl.setSerializationLib(serDeClassName);

    if (getFieldDelim() != null) {
      tbl.setSerdeParam(serdeConstants.FIELD_DELIM, getFieldDelim());
      tbl.setSerdeParam(serdeConstants.SERIALIZATION_FORMAT, getFieldDelim());
    }
    if (getFieldEscape() != null) {
      tbl.setSerdeParam(serdeConstants.ESCAPE_CHAR, getFieldEscape());
    }

    if (getCollItemDelim() != null) {
      tbl.setSerdeParam(serdeConstants.COLLECTION_DELIM, getCollItemDelim());
    }
    if (getMapKeyDelim() != null) {
      tbl.setSerdeParam(serdeConstants.MAPKEY_DELIM, getMapKeyDelim());
    }
    if (getLineDelim() != null) {
      tbl.setSerdeParam(serdeConstants.LINE_DELIM, getLineDelim());
    }
    if (getNullFormat() != null) {
      tbl.setSerdeParam(serdeConstants.SERIALIZATION_NULL_FORMAT, getNullFormat());
    }
    if (getSerdeProps() != null) {
      getSerdeProps().forEach(tbl::setSerdeParam);
    }

    setColumnsAndStorePartitionTransformSpecOfTable(getCols(), getPartCols(), conf, tbl);

    if (getBucketCols() != null) {
      tbl.setBucketCols(getBucketCols());
    }
    if (getSortCols() != null) {
      tbl.setSortCols(getSortCols());
    }
    if (getComment() != null) {
      tbl.setProperty("comment", getComment());
    }
    if (getLocation() != null) {
      tbl.setDataLocation(new Path(getLocation()));
    }

    if (getSkewedColNames() != null) {
      tbl.setSkewedColNames(getSkewedColNames());
    }
    if (getSkewedColValues() != null) {
      tbl.setSkewedColValues(getSkewedColValues());
    }

    tbl.getTTable().setTemporary(isTemporary());

    tbl.setStoredAsSubDirectories(isStoredAsSubDirectories());

    tbl.setInputFormatClass(getInputFormat());
    tbl.setOutputFormatClass(getOutputFormat());

    // only persist input/output format to metadata when it is explicitly specified.
    // Otherwise, load lazily via StorageHandler at query time.
    if (getInputFormat() != null && !getInputFormat().isEmpty()) {
      tbl.getTTable().getSd().setInputFormat(tbl.getInputFormatClass().getName());
    }
    if (getOutputFormat() != null && !getOutputFormat().isEmpty()) {
      tbl.getTTable().getSd().setOutputFormat(tbl.getOutputFormatClass().getName());
    }

    if (CreateTableOperation.doesTableNeedLocation(tbl)) {
      // If location is specified - ensure that it is a full qualified name
      CreateTableOperation.makeLocationQualified(tbl, conf);
    }

    if (isExternal()) {
      tbl.setProperty("EXTERNAL", "TRUE");
      tbl.setTableType(TableType.EXTERNAL_TABLE);
    }

    // If the sorted columns is a superset of bucketed columns, store this fact.
    // It can be later used to
    // optimize some group-by queries. Note that, the order does not matter as
    // long as it in the first
    // 'n' columns where 'n' is the length of the bucketed columns.
    if ((tbl.getBucketCols() != null) && (tbl.getSortCols() != null)) {
      List<String> bucketCols = tbl.getBucketCols();
      List<Order> sortCols = tbl.getSortCols();

      if ((!sortCols.isEmpty()) && (sortCols.size() >= bucketCols.size())) {
        boolean found = true;

        for (String bucketCol : bucketCols) {
          boolean colFound = false;
          for (int i = 0; i < bucketCols.size(); i++) {
            if (bucketCol.equals(sortCols.get(i).getCol())) {
              colFound = true;
              break;
            }
          }
          if (!colFound) {
            found = false;
            break;
          }
        }
        if (found) {
          tbl.setProperty("SORTBUCKETCOLSPREFIX", "TRUE");
        }
      }
    }

    if (colStats != null) {
      ColumnStatisticsDesc colStatsDesc = new ColumnStatisticsDesc(colStats.getStatsDesc());
      colStatsDesc.setCatName(tbl.getCatName());
      colStatsDesc.setDbName(tbl.getDbName());
      colStatsDesc.setTableName(tbl.getTableName());
      String engine = colStats.getEngine();
      if (engine == null) {
        engine = org.apache.hadoop.hive.conf.Constants.HIVE_ENGINE;
      }
      ColumnStatistics columnStatistics = new ColumnStatistics(colStatsDesc, colStats.getStatsObj());
      columnStatistics.setEngine(engine);
      tbl.getTTable().setColStats(columnStatistics);
      // Statistics will have an associated write Id for a transactional table. We need it to
      // update column statistics.
      if (replWriteId > 0) {
        tbl.getTTable().setWriteId(replWriteId);
      }
    }

    // When replicating the statistics for a table will be obtained from the source. Do not
    // reset it on replica.
    if (replicationSpec == null || !replicationSpec.isInReplicationScope()) {
      // Remove COLUMN_STATS_ACCURATE=true from table's parameter, let the HMS determine if
      // there is need to add column stats dependent on the table's location.
      StatsSetupConst.setStatsStateForCreateTable(tbl.getTTable().getParameters(), null,
          StatsSetupConst.FALSE);
      if (!this.isCTAS && !tbl.isPartitioned() && !tbl.isTemporary() &&
          conf.getBoolVar(HiveConf.ConfVars.HIVE_STATS_AUTOGATHER)) {
        // Put the flag into the dictionary in order not to pollute the table,
        // ObjectDictionary is meant to convey repeatitive messages.
        ObjectDictionary dictionary = tbl.getTTable().isSetDictionary() ?
            tbl.getTTable().getDictionary() : new ObjectDictionary();
        List<ByteBuffer> buffers = new ArrayList<>();
        String statsSetup = StatsSetupConst.ColumnStatsSetup.getStatsSetupAsString(true,
            // Ignore all Iceberg leftover files when storageHandler.isTableIdentifierSupported() is true,
            // as the method is only enabled in Iceberg currently.
            storageHandler != null && storageHandler.isTableMetaRefSupported(),
            MetaStoreUtils.getColumnNames(tbl.getCols()));
        buffers.add(ByteBuffer.wrap(statsSetup.getBytes(StandardCharsets.UTF_8)));
        dictionary.putToValues(StatsSetupConst.STATS_FOR_CREATE_TABLE, buffers);
        tbl.getTTable().setDictionary(dictionary);
      }
    }

    if (ownerName != null) {
      tbl.setOwner(ownerName);
    }
    return tbl;
  }

  public Long getReplWriteId() {
    return replWriteId;
  }

  public void setReplWriteId(Long replWriteId) {
    this.replWriteId = replWriteId;
  }

  public void fromTable(org.apache.hadoop.hive.metastore.api.Table tTable) {
    if (tTable.getSd() != null  && tTable.getSd().getLocation() != null) {
      setLocation(tTable.getSd().getLocation());
    }
    setExternal(TableType.EXTERNAL_TABLE.toString().equals(tTable.getTableType()));
    setTblProps(tTable.getParameters());
    tblProps.remove(hive_metastoreConstants.CTAS_LEGACY_CONFIG);
  }
}
