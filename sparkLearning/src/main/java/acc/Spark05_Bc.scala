package acc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 10:23
 */
object Spark05_Bc {

  def main(args: Array[String]): Unit = {

    val sparConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparConf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val map = mutable.Map(("a", 4),("b", 5),("c", 6))

    rdd1.map{
      case(w,c) => {
        val I:Int = map.getOrElse(w, 0)
        (w,(c,I))
      }
    }.collect().foreach(println)

    sc.stop()

  }

}
