package RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/22 10:51
 */
object spark02_rddOutSide {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark")
    val sparkContext = new SparkContext(sparkConf)
    val fileRDD: RDD[String] = sparkContext.textFile("input")
    fileRDD.collect().foreach(println)
    sparkContext.stop()
  }

}
