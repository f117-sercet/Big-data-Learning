package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.xml.Utility.Escapes.pairs

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/1 14:49
 */
//WordCount 第三版：3 秒一个批次，窗口 12 秒，滑步 6 秒。
object WorldCount2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(3))
    ssc.checkpoint("./ck")

    // Create a DStream that will connect to hostname:port, like localhost:9999

    val lines = ssc.socketTextStream("hadoop102", 9999)
    val words = lines.flatMap(_.split(""))
    //Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKeyAndWindow((a:Int,b:Int) => (a + b),Seconds(12), Seconds(6))

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()

    
  }

}
