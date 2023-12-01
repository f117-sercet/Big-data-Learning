package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.json4s.JsonAST.JNull.values
import org.json4s.scalap.scalasig.ClassFileParser.state

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/1 14:37
 */
object WorldCount {
  def main(args: Array[String]): Unit = {
    // 定义更新状态方法，参数values为当前批次单词频度，state为以往批次单词频度
    val updateFunc =(values:Seq[Int],state:Option[Int]) =>{
      val  currentCount = values.foldLeft(0)(_+_)
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("NetworkWorkCount")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("./ck")

    val lines = ssc.socketTextStream("hadoop102", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))

    // 使用 updateStateByKey 来更新状态，统计从运行开始以来单词总的次数
    val stateDstream = pairs.updateStateByKey[Int](updateFunc)

    stateDstream.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

}
