package RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/22 15:05
 */
object spark_Executor {


  def main(args: Array[String]): Unit = {
      val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark-executor")

    var sparContext = new SparkContext(sparkConf)

    val dataRDD:RDD[Int] = sparContext.makeRDD(
      List(1,2,3,4,5,6,7,8),4
    )
    val fileRDD:RDD[String] = sparContext.textFile("input",2)
    fileRDD.collect().foreach(println)
  }


}
