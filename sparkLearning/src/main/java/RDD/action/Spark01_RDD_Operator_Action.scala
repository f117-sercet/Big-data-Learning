package RDD.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/27 15:17
 */
object Spark01_RDD_Operator_Action {


  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8))

    // TODO - 行动算子
    // 所谓的行动算子，其实就是触发作业(Job)执行的方法
    // 底层代码调用的是环境对象的runJob方法
    // 底层代码中会创建ActiveJob，并提交执行。
    rdd.collect()

    sc.stop()
  }

}
