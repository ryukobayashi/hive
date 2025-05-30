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

package org.apache.hadoop.hive.metastore;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.annotation.MetastoreUnitTest;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.client.builder.DatabaseBuilder;
import org.apache.hadoop.hive.metastore.client.builder.PartitionBuilder;
import org.apache.hadoop.hive.metastore.client.builder.TableBuilder;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf.ConfVars;
import org.apache.hadoop.hive.metastore.events.ListenerEvent;
import org.apache.hadoop.hive.metastore.security.HadoopThriftAuthBridge;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.experimental.categories.Category;

/**
 * Ensure that the status of MetaStore events depend on the RawStore's commit status.
 */
@Category(MetastoreUnitTest.class)
public class TestMetaStoreEventListenerOnlyOnCommit {

  private Configuration conf;
  private HiveMetaStoreClient msc;

  public static class DummyRawStoreControlledCommit extends ObjectStore {
    private static boolean shouldCommitSucceed = true;
    public static void setCommitSucceed(boolean flag) {
      shouldCommitSucceed = flag;
    }

    @Override
    public boolean commitTransaction() {
      if (shouldCommitSucceed) {
        return super.commitTransaction();
      } else {
        return false;
      }
    }
  }

  @Before
  public void setUp() throws Exception {
    DummyRawStoreControlledCommit.setCommitSucceed(true);

    System.setProperty(ConfVars.EVENT_LISTENERS.toString(), DummyListener.class.getName());
    System.setProperty(ConfVars.RAW_STORE_IMPL.toString(),
            DummyRawStoreControlledCommit.class.getName());

    conf = MetastoreConf.newMetastoreConf();
    MetastoreConf.setLongVar(conf, ConfVars.THRIFT_CONNECTION_RETRIES, 3);
    MetastoreConf.setBoolVar(conf, ConfVars.HIVE_SUPPORT_CONCURRENCY, false);
    MetaStoreTestUtils.setConfForStandloneMode(conf);
    MetaStoreTestUtils.startMetaStoreWithRetry(HadoopThriftAuthBridge.getBridge(), conf);
    msc = new HiveMetaStoreClient(conf);

    DummyListener.notifyList.clear();
  }

  @Test
  public void testEventStatus() throws Exception {
    int listSize = 0;
    List<ListenerEvent> notifyList = DummyListener.notifyList;
    assertEquals(notifyList.size(), listSize);

    String dbName = "tmpDb";
    Database db = new DatabaseBuilder()
        .setName(dbName)
        .setCatalogName(Warehouse.DEFAULT_CATALOG_NAME)
        .create(msc, conf);

    listSize += 1;
    notifyList = DummyListener.notifyList;
    assertEquals(notifyList.size(), listSize);
    assertTrue(DummyListener.getLastEvent().getStatus());

    String tableName = "unittest_TestMetaStoreEventListenerOnlyOnCommit";
    Table table = new TableBuilder()
        .inDb(db)
        .setTableName(tableName)
        .addCol("id", "int")
        .addPartCol("ds", "string")
        .create(msc, conf);
    listSize += 1;
    notifyList = DummyListener.notifyList;
    assertEquals(notifyList.size(), listSize);
    assertTrue(DummyListener.getLastEvent().getStatus());

    new PartitionBuilder()
        .inTable(table)
        .addValue("foo1")
        .addToTable(msc, conf);
    listSize += 1;
    notifyList = DummyListener.notifyList;
    assertEquals(notifyList.size(), listSize);
    assertTrue(DummyListener.getLastEvent().getStatus());

    DummyRawStoreControlledCommit.setCommitSucceed(false);

    new PartitionBuilder()
        .inTable(table)
        .addValue("foo2")
        .addToTable(msc, conf);
    listSize += 1;
    notifyList = DummyListener.notifyList;
    assertEquals(notifyList.size(), listSize);
    assertFalse(DummyListener.getLastEvent().getStatus());

  }
}
