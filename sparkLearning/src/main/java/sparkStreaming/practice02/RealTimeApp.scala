package sparkStreaming.practice02

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import sparkStreaming.util.{MykafkaUtil, PropertiesUtil}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/7 18:04
 */
object RealTimeApp {

  def main(args: Array[String]): Unit = {

    // 1 创建 SparkConf
    val sparkConf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("RealTimeApp")
    // 2.创建 StreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 读取kafka数据
    val topic: String = PropertiesUtil.load("config.properties").getProperty("kafka.topic")
    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = MykafkaUtil.getKafkaStream(topic, ssc
  }
}
