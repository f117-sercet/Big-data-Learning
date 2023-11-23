package acc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 14:36
 */
object Spark06_bc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    val map = mutable.Map(("a", 4),("b", 5),("c", 6))

    // 封装广播变量
    val bc = sc.broadcast(map)

    rdd1.map{
      case(w,c) => {

        // 方法广播变量
        val l:Int = bc.value.getOrElse(w, 0)
        (w,(c,l))
      }
    }.collect().foreach(println)
    sc.stop()
  }
}
