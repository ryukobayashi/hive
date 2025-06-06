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

package org.apache.hadoop.hive.common;

import java.io.File;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.common.annotations.VisibleForTesting;

/**
 * Utilities common to logging operations.
 */
public class LogUtils {

  private static final String HIVE_L4J = "hive-log4j2.properties";
  private static final String HIVE_EXEC_L4J = "hive-exec-log4j2.properties";
  private static final Logger l4j = LoggerFactory.getLogger(LogUtils.class);

  /**
   * Constants for log masking
   */
  private static final String KEY_TO_MASK_WITH = "password";
  private static final String MASKED_VALUE = "###_MASKED_###";

  /**
   * Constants of the key strings for the logging ThreadContext.
   */
  public static final String SESSIONID_LOG_KEY = "sessionId";
  public static final String QUERYID_LOG_KEY = "queryId";
  public static final String DAGID_KEY = "dagId";
  public static final String OPERATIONLOG_LEVEL_KEY = "operationLogLevel";
  public static final String OPERATIONLOG_LOCATION_KEY = "operationLogLocation";

  @SuppressWarnings("serial")
  public static class LogInitializationException extends Exception {
    public LogInitializationException(String msg) {
      super(msg);
    }
  }

  /**
   * Initialize log4j.
   *
   * @return an message suitable for display to the user
   * @throws LogInitializationException if log4j fails to initialize correctly
   */
  public static String initHiveLog4j()
    throws LogInitializationException {
    return initHiveLog4jCommon(HiveConf.ConfVars.HIVE_LOG4J_FILE);
  }

  /**
   * Initialize log4j for execution mode.
   *
   * @return an message suitable for display to the user
   * @throws LogInitializationException if log4j-exec fails to initialize correctly
   */
  public static String initHiveExecLog4j()
    throws LogInitializationException {
    return initHiveLog4jCommon(HiveConf.ConfVars.HIVE_EXEC_LOG4J_FILE);
  }

  private static String initHiveLog4jCommon(ConfVars confVarName)
      throws LogInitializationException {
    HiveConf conf = new HiveConf();
    return initHiveLog4jCommon(conf, confVarName);
  }

  @VisibleForTesting
  public static String initHiveLog4jCommon(HiveConf conf, ConfVars confVarName)
    throws LogInitializationException {
    if (HiveConf.getVar(conf, confVarName).equals("")) {
      // if log4j configuration file not set, or could not found, use default setting
      return initHiveLog4jDefault(conf, "", confVarName);
    } else {
      // if log4j configuration file found successfully, use HiveConf property value
      String log4jFileName = HiveConf.getVar(conf, confVarName);
      File log4jConfigFile = new File(log4jFileName);
      boolean fileExists = log4jConfigFile.exists();
      if (!fileExists) {
        // if property specified file not found in local file system
        // use default setting
        return initHiveLog4jDefault(
          conf, "Not able to find conf file: " + log4jConfigFile, confVarName);
      } else {
        // property specified file found in local file system
        // use the specified file
        if (confVarName == HiveConf.ConfVars.HIVE_EXEC_LOG4J_FILE) {
          String queryId = HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_QUERY_ID);
          if(queryId == null || (queryId = queryId.trim()).isEmpty()) {
            queryId = "unknown-" + System.currentTimeMillis();
          }
          System.setProperty(HiveConf.ConfVars.HIVE_QUERY_ID.toString(), queryId);
        }
        final boolean async = checkAndSetAsyncLogging(conf);
        // required for MDC based routing appender so that child threads can inherit the MDC context
        System.setProperty(DefaultThreadContextMap.INHERITABLE_MAP, "true");
        Configurator.initialize(null, log4jFileName);
        logConfigLocation(conf);
        return "Logging initialized using configuration in " + log4jConfigFile + " Async: " + async;
      }
    }
  }

  public static boolean checkAndSetAsyncLogging(final Configuration conf) {
    final boolean asyncLogging = HiveConf.getBoolVar(conf, ConfVars.HIVE_ASYNC_LOG_ENABLED);
    if (asyncLogging) {
      System.setProperty("Log4jContextSelector",
          "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
      // default is ClassLoaderContextSelector which is created during automatic logging
      // initialization in a static initialization block.
      // Changing ContextSelector at runtime requires creating new context factory which will
      // internally create new context selector based on system property.
      LogManager.setFactory(new Log4jContextFactory());
    }
    return asyncLogging;
  }

  private static String initHiveLog4jDefault(
    HiveConf conf, String logMessage, ConfVars confVarName)
    throws LogInitializationException {
    URL hive_l4j = null;
    switch (confVarName) {
      case HIVE_EXEC_LOG4J_FILE:
        hive_l4j = LogUtils.class.getClassLoader().getResource(HIVE_EXEC_L4J);
        if (hive_l4j == null) {
          hive_l4j = LogUtils.class.getClassLoader().getResource(HIVE_L4J);
        }
        System.setProperty(HiveConf.ConfVars.HIVE_QUERY_ID.toString(),
          HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_QUERY_ID));
        break;
      case HIVE_LOG4J_FILE:
        hive_l4j = LogUtils.class.getClassLoader().getResource(HIVE_L4J);
        break;
      default:
        break;
    }
    if (hive_l4j != null) {
      final boolean async = checkAndSetAsyncLogging(conf);
      System.setProperty(DefaultThreadContextMap.INHERITABLE_MAP, "true");
      Configurator.initialize(null, hive_l4j.toString());
      logConfigLocation(conf);
      return (logMessage + "\n" + "Logging initialized using configuration in " + hive_l4j +
          " Async: " + async);
    } else {
      throw new LogInitializationException(
        logMessage + "Unable to initialize logging using "
        + LogUtils.HIVE_L4J + ", not found on CLASSPATH!");
    }
  }

  private static void logConfigLocation(HiveConf conf) throws LogInitializationException {
    // Log a warning if hive-default.xml is found on the classpath
    if (conf.getHiveDefaultLocation() != null) {
      l4j.warn("DEPRECATED: Ignoring hive-default.xml found on the CLASSPATH at "
        + conf.getHiveDefaultLocation().getPath());
    }
    // Look for hive-site.xml on the CLASSPATH and log its location if found.
    if (conf.getHiveSiteLocation() == null) {
      l4j.warn("hive-site.xml not found on CLASSPATH");
    } else {
      l4j.debug("Using hive-site.xml found on CLASSPATH at "
        + conf.getHiveSiteLocation().getPath());
    }
  }

  /**
   * Returns MASKED_VALUE if the key contains KEY_TO_MASK_WITH or the original property otherwise.
   * Used to mask environment variables, and properties in logs which contain passwords
   * @param key The property key to check
   * @param value The original value of the property
   * @return The masked property value
   */
  public static String maskIfPassword(String key, String value) {
    if (key!=null && value!=null) {
      if (key.toLowerCase().indexOf(KEY_TO_MASK_WITH) != -1) {
        return MASKED_VALUE;
      }
    }
    return value;
  }

  /**
   * Register logging context so that log system can print QueryId, SessionId, etc for each message
   */
  public static void registerLoggingContext(Configuration conf) {
    if (HiveConf.getBoolVar(conf, HiveConf.ConfVars.HIVE_SERVER2_LOGGING_OPERATION_ENABLED)) {
      MDC.put(SESSIONID_LOG_KEY, HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_SESSION_ID));
      MDC.put(QUERYID_LOG_KEY, HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_QUERY_ID));
      MDC.put(OPERATIONLOG_LEVEL_KEY, HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_SERVER2_LOGGING_OPERATION_LEVEL));
      MDC.put(OPERATIONLOG_LOCATION_KEY, HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_SERVER2_LOGGING_OPERATION_LOG_LOCATION));
      l4j.info("Thread context registration is done.");
    } else {
      l4j.info("Thread context registration is skipped.");
    }
  }

  /**
   * Unregister logging context
   */
  public static void unregisterLoggingContext() {
    // Remove the keys added, don't use clear, as it may clear all other things which are not intended to be removed.
    MDC.remove(SESSIONID_LOG_KEY);
    MDC.remove(QUERYID_LOG_KEY);
    MDC.remove(DAGID_KEY); // it was put by TezTask after submitting DAG
    MDC.remove(OPERATIONLOG_LEVEL_KEY);
    MDC.remove(OPERATIONLOG_LOCATION_KEY);
    l4j.info("Unregistered logging context.");
  }

  /**
   * Get path of the log file for user to see on the WebUI.
   */
  public static String getLogFilePath() {
    String logFilePath = null;
    org.apache.logging.log4j.Logger rootLogger = LogManager.getRootLogger();
    if (rootLogger instanceof org.apache.logging.log4j.core.Logger coreLogger) {
      for (Appender appender : coreLogger.getAppenders().values()) {
        if (appender instanceof FileAppender fileAppender) {
          logFilePath = fileAppender.getFileName();
        } else if (appender instanceof RollingFileAppender fileAppender) {
          logFilePath = fileAppender.getFileName();
        } else if (appender instanceof RollingRandomAccessFileAppender fileAppender) {
          logFilePath = fileAppender.getFileName();
        }
      }
    }
    return logFilePath;
  }

  public static void putToMDC(String key, String value) {
    MDC.put(key, value);
  }
}
