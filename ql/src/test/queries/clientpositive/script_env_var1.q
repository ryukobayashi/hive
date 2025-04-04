--! qt:dataset:src
-- Verifies that script operator ID environment variables have unique values
-- in each instance of the script operator.
-- Disable SharedWorkOptimization in order to create 2 Script operators.
set hive.optimize.shared.work=false;
SELECT count(1) FROM
( SELECT * FROM (SELECT TRANSFORM('echo $HIVE_SCRIPT_OPERATOR_ID') USING 'sh' AS key FROM src order by key LIMIT 1)x UNION ALL
  SELECT * FROM (SELECT TRANSFORM('echo $HIVE_SCRIPT_OPERATOR_ID') USING 'sh' AS key FROM src order by key LIMIT 1)y ) a GROUP BY key;
