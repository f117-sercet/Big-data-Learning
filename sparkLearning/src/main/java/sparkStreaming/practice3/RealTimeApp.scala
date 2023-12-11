package sparkStreaming.practice3

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import sparkStreaming.practice.{BlackListHandler, Spark01Adverties}
import sparkStreaming.practice02.DateAreaCityAdCountHandler
import sparkStreaming.util.{JdbcUtil, MykafkaUtil, PropertiesUtil}

import java.sql.Connection

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/11 9:51
 */
object RealTimeApp {

  def main(args: Array[String]): Unit = {

    // 创建 SparkConf
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RealTimeApp")

    // 2.创建StreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 3.读取kafka数据
    val topic: String = PropertiesUtil.load("config.properties").getProperty("kafka.topic")

    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = MykafkaUtil.getKafkaStream(topic, ssc)

    //4.将每一行数据转换为样例类对象
    val adsLogDStream:DStream[Spark01Adverties] = kafkaDStream.map(record => {
      //a.取出value 并按照“”切分
      val arr: Array[String] = record.value().split("")
      //b.封装为样例类对象
      Spark01Adverties(arr(0).toLong, arr(1), arr(2), arr(3), arr(4))
    })
    //5.根据 MySQL 中的黑名单表进行数据过滤
    val filterAdsLogDStream:DStream[Spark01Adverties] = adsLogDStream.filter(adsLog => {
      // 查询数据库，查看当前用户是否存在
      val connection: Connection = JdbcUtil.getConnection
      val bool: Boolean = JdbcUtil.isExist(connection, "select *from black_list where userId = ?", Array(adsLog.userid))
      connection.close()
      !bool
    })
    filterAdsLogDStream.cache()

    // 对没有被加入黑名单的用户统计当前批次单日各个用户对各个广告点击的次数，
    // 并更新至Mysql，之后查询更新之后的数据，判断是否超过100次。如果超过则加入黑名单
    BlackListHandler.addBlackList(filterAdsLogDStream)
    
    // 统计每天各大区各个城市广告点击总数并保存至Mysql中
    DateAreaCityAdCountHandler.saveDateAreaCityAdCountToMysql(filterAdsLogDStream)
    
    //统计最近一小时广告分时点击数
    val adToHmCountListDStream: DStream[(String, List[(String, Long)])] = LastHourAdCountHandler.getAdHourMintToCount(filterAdsLogDStream)

    // 9 打印
    adToHmCountListDStream.print()
    //10 开启任务
    ssc.start()
    ssc.awaitTermination()
  }

}
