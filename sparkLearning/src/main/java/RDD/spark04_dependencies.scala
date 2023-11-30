package RDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.Console.println

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/24 8:50
 */
object spark04_dependencies  {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("spark")
    val sc: SparkContext = new SparkContext(conf)
    val fileRDD: RDD[String] = sc.textFile("C:\\Users\\LENOVO\\Desktop\\word.txt")
    println (fileRDD.dependencies)
    println ("----------------------")
    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    println (wordRDD.dependencies)
    println ("----------------------")
    val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
    println (mapRDD.dependencies)
    println ("----------------------")
    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    println (resultRDD.dependencies)
    resultRDD.collect()
  }

}
