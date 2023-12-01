package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.ReceiverInputDStream

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/1 9:23
 */
object TransForm {
  def main(args: Array[String]): Unit = {
    //创建 SparkConf
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

    //创建 StreamingContext
     val ssc = new StreamingContext(sparkConf, Seconds(3))
    //创建 DStream
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("linux1",
      9999)

    // 转换RDD操作
    val wordAndCountDStream = lineDStream.transform(rdd => {
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val wordAndOne: RDD[(String, Int)] = words.map((_, 1))
      val value: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _)
      value
    })
    //打印
     wordAndCountDStream.print

    // 启动
    ssc.start()
    ssc.awaitTermination()

  }
}
