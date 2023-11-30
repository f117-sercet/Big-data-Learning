package sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/28 9:49
 */
object Spark01_Hive {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "atguigu")
    // TODO 创建SparkSQL的运行环境
    val spark = SparkSession.builder()
      .config("spark.sql.warehouse.dir", "hdfs://hadoop102:8020/user/hive/warehouse")
      .enableHiveSupport()
      .master("local[*]")
      .appName("sql")
      .getOrCreate()
    // 使用SparkSQL连接外置的Hive
    // 1. 拷贝Hive-size.xml文件到classpath下
    // 2. 启用Hive的支持
    // 3. 增加对应的依赖关系（包含MySQL驱动）
    spark.sql("select * from sparklearning.city_info").show()

    // TODO 关闭环境
    spark.close()

  }

}
