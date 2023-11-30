package RDD.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/27 15:41
 */
object Spark07_RDD_Operator_Action {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List[Int]())

    val user = new User()

      // 闭包检测
      rdd.foreach((
        num =>{
          println("age =" + (user.age + num))
        }
      ))
    sc.stop()
  }

  class User {
    var age: Int =30
  }

}
