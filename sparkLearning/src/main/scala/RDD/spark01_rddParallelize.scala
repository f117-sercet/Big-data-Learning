package RDD

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/22 10:34
 */
object spark01_rddParallelize {


  // 从集合中创建RDD，Spark 主要提供了两个方法：parallelize 和makeRDD
  def main(args: Array[String]): Unit = {
    var sparkConf  = new SparkConf().setMaster("local[*]").setAppName("spark")
    var sparkContext = new SparkContext(sparkConf)
    var rdd1 = sparkContext.parallelize(
      List(1,2,3,4)
    )
    var rdd2 = sparkContext.makeRDD(
      List(1,2,3,4)
    )
    rdd1.collect().foreach(println)
    rdd2.collect().foreach(println)
    sparkContext.stop()
  }
}
