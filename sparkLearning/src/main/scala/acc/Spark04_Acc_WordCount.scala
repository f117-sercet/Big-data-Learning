package acc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/23 10:08
 */
class Spark04_Acc_WordCount {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List("hello", "spark", "hello"))

    // 累加器：wordCount
    // 创建累加器对象
    val wcAcc = new MyAccumulator()
    // 向spark进行注册
    sc.register(wcAcc,"worldCountAcc")

    rdd.foreach(
      word =>{
        // 数据累加(使用累加器)
        wcAcc.add(word)
      }
    )
    // 获取累加器累加的结果
    println(wcAcc.value)
    sc.stop()
  }

}
