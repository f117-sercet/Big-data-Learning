package sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/27 17:09
 */
object SparkSQL01_Demo {
  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象 val conf: SparkConf = new
    var conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")

    // 创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //RDD=>DataFrame=>DataSet 转换需要引入隐式转换规则，否则无法转换
    // spark 不是包名，是上下文环境对象名
    import spark.implicits._

    // 读取Json文件，创建DataFrame
    val df: DataFrame = spark.read.json("C:\\Users\\LENOVO\\Desktop\\user.txt")

    // SQL风格语法
    df.createOrReplaceTempView("User")
    spark.sql("select avg(age) from User").show

    //DSL风格
    df.select("username", "age").show()

    //*****RDD=>DataFrame=>DataSet*****
    //RDD
    val rdd1: RDD[(Int, String, Int)] = spark.sparkContext.
      makeRDD(List((1, "zhangsan", 30), (2, "lisi", 28), (3, "wangwu", 20)))

    //DataFrame
    val df1:DataFrame = rdd1.toDF("id", "name", "age")
    df1.show()

    //*****DataSet=>DataFrame=>RDD***** //DataFrame
    val ds1: Dataset[User] = df1.as[User]
    ds1.show()

    //*****DataSet=>DataFrame=>RDD*****
    // DataFrame
    val df2:DataFrame = ds1.toDF()
    //RDD 返回的 RDD 类型为 Row，里面提供的 getXXX 方法可以获取字段值，
    // 类似 jdbc 处理结果集， 但是索引从 0 开始

    val rdd2: RDD[Row] = df2.rdd
    rdd2.foreach(a=>println(a.getString(1)))
    //*****RDD=>DataSet*****
     rdd1.map{
       case (id,name,age) =>User(id,name,age)
     }.toDS()

    //*****DataSet=>=>RDD*****
    ds1.rdd

    // 释放资源
  }
}
case class User(id:Int,name:String,age:Int)
