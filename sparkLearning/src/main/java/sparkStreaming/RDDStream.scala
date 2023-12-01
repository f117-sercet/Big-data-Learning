package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.expressions.Second
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/1 8:28
 */
object RDDStream {

  def main(args: Array[String]): Unit = {

    // 1.初始化spark配置信息
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDDStream")

    // 2.初始化SparkStreamingContext
    val ssc = new StreamingContext(conf, Seconds(4))

    // 3. 创建RDD队列
    val rddQueue = new mutable.Queue[RDD[Int]]()

    // 4.创建QueueInputDStream
    val inputStream = ssc.queueStream(rddQueue, oneAtATime = false)

    // 5.处理队列中的RDD数据
    val mappedStream = inputStream.map((_,1))
    val reducedStream = mappedStream.reduceByKey(_ + _)

    //6.打印结果
    reducedStream.print()

    //7.启动任务
    ssc.start()

    // 8.循环创建并向RDD队列中放入RDD
    for (i <- 1 to 5){

      ssc.sparkContext.makeRDD(1 to 300,10)
      Thread.sleep(3000)
    }
    ssc.awaitTermination()
  }

}
