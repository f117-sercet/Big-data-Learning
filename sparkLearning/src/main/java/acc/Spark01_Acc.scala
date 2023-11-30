package acc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @create: Created in 2023/11/23 8:55
 */
object Spark01_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)
    var sum = 0
    sc.makeRDD(List(1,2,3,4)).foreach(
      // reduce : 分区内计算，分区间计算
      //val i: Int = rdd.reduce(_+_)
      //println(i)
      num => {
        sum+=num
      }
    )
    println("sum: " + sum)
    sc.stop()

  }

}
