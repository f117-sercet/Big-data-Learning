package sparkSql

import org.apache.spark.sql.SparkSession

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/28 9:49
 */
object Spark01_Hive {

  def main(args: Array[String]): Unit = {
    SparkSession.builder().enableHiveSupport()
      .master("local[*]")
      .appName("sql")
      .getOrCreate()
  }

}
