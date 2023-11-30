package sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/29 16:37
 */
object Spark02_SparkSQL_UDF {

  def main(args: Array[String]): Unit = {

    // 创建运行环境
    val sparkSQLConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkSQLConf).getOrCreate()
    import spark.implicits._

    val df = spark.read.json("C:\\Users\\LENOVO\\Desktop\\user.json")
    df.createOrReplaceTempView("user")
    df.show()
    spark.udf.register("prefixName",(name:String)=>{
      "Name:"+name
    })
    spark.sql("select age,prefixName(username) from user").show()

    // TODO:关闭环境


  }

}
