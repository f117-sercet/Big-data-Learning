package sparkStreaming.practice02

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import sparkStreaming.practice.{BlackListHandler, Spark01Adverties}
import sparkStreaming.util.{JdbcUtil, MykafkaUtil, PropertiesUtil}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/7 18:04
 */
object RealTimeApp {

  def main(args: Array[String]): Unit = {
    //1.创建 SparkConf
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RealTimeApp")
    //2.创建 StreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    //3.读取 Kafka 数据
    val topic: String = PropertiesUtil.load("config.properties").getProperty("kafka.topic")
    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = MykafkaUtil.getKafkaStream(topic, ssc)
    //4.将每一行数据转换为样例类对象
    val adsLogDStream: DStream[Spark01Adverties] = kafkaDStream.map(
      record => {
        //a.取出 value 并按照" "切分
        val arr: Array[String] = record.value().split(" ")
        //b.封装为样例类对象
        Spark01Adverties(arr(0).toLong, arr(1), arr(2), arr(3), arr(4))
      })

    // 根据mysql中的黑名单表进行数据过滤
    val filterAdsLogDStream = adsLogDStream.filter(adsLog => {
      // 查询Mysql，查看当前用户是否存在
      val connection = JdbcUtil.getConnection
      val bool = JdbcUtil.isExist(connection, "select *from black_list where user_id =?", Array(adsLog.userid))
      connection.close()
      !bool
    })
    filterAdsLogDStream.cache()
    // 6.对没有被加入黑名单的用户统计当前佩慈单日各个用户对广告点击的总次数，并更新至Mysql
    BlackListHandler.saveBlackListToMysql(filterAdsLogDStream)

    // 统计每天各大区各个城市广告点击次数并保存至Mysql中

    //测试打印
    filterAdsLogDStream.cache()
    filterAdsLogDStream.count().print()


    //启动任务
     ssc.start()
    ssc.awaitTermination()
  }
}