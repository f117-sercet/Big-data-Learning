package sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.datanucleus.store.types.wrappers.backed
import org.datanucleus.store.types.wrappers.backed.Properties

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/28 8:41
 */
object SparkSQL01_ReadandLoad {

  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象 val conf: SparkConf = new
    var conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")

    // 创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //RDD=>DataFrame=>DataSet 转换需要引入隐式转换规则，否则无法转换
    // spark 不是包名，是上下文环境对象名
    import spark.implicits._

     // 读取csv
    /*spark.read.format("csv").option("seq", ";").option("inferSchema","true")
      .option("header","true").load("data/user.csv")*/

    // 读取mysql,方法一
    // 通用的load方法读取
  /*  spark.read.format("jdbc").option("url", "jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true")
      .option("driver","com.mysql.cj.jdbc.Driver")
      .option("user","root")
      .option("password","123123")
      .option("dbtable", "city")
      .load.show()*/


    // 方法二：
    spark.read.format("jdbc").options(Map("url" -> "jdbc:mysql://localhost:3306/srb_core?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&user=root&password=123123",
        "dbtable" -> "integral_grade",
      "driver"->"com.mysql.cj.jdbc.Driver"))
      .load()
      .show

    // 方式三：使用jdbc方法读取
   /*  val props: Properties = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "123123")
    val df: DataFrame = spark.read.jdbc("jdbc:mysql://linux1:3306/spark-sql", "user", props)
    //df.show*/




    spark.stop()




  }

}
