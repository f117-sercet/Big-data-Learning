package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/30 14:36
 */
object StreamWordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamWordCount")

    // 初始化
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 通过监控端口创建DStream，读进来的数据为行。
    val lineStreams=ssc.socketTextStream("hadoop102", 9999)
    //将每一行数据做切分，形成一个个单词
    val wordStreams = lineStreams.flatMap(_.split(" "))
    // 将单词映射成元组(word,1)
    val wordAndOneStreams = wordStreams.map((_, 1))

    // 将相同的单词次数做统计
    val wordAndCountStreams = wordAndOneStreams.reduceByKey(_ + _)

    wordAndCountStreams.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
