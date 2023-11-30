package wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/22 15:37
 */
object wordCount3 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")

    val sc = new SparkContext(sparkConf)
    wordCountExecutor2(sc)
    sc.stop()
  }

  // groupBy
  def wordCountExecutor1(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val wordCount:RDD[(String,Int)] = rdd.flatMap(_.split("")).
      groupBy(word => word)
      .mapValues(iter => (iter.size))

    wordCount.foreach(println)
  }

  // groupByKey
  def wordCountExecutor2(sc:SparkContext):Unit= {

    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val group: RDD[(String, Iterable[Int])] = wordOne.groupByKey()
    val wordCount: RDD[(String, Int)] = group.mapValues(iter => iter.size)
  }

  // reduceByKey
  def wordCountExecutor3(sc:SparkContext):Unit={

    sc.makeRDD(List("Hello Scala","Hello Spark"))
      .flatMap(_.split(""))
      .map((_,1))
      .reduceByKey(_+_)
  }
}
