package sparkStreaming.practice02

import org.apache.spark.streaming.dstream.DStream
import sparkStreaming.practice.Spark01Adverties
import sparkStreaming.util.JdbcUtil

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/6 17:40
 */
object DateAreaCityAdCountHandler {

  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def saveDateAreaCityAdCountToMysql(filterAdsLogDStream: DStream[Spark01Adverties]):Unit
  ={
    // 统计每天各大区各个城市广告点击数
    val dateAreaCityAdToCount: DStream[((String, String, String, String), Long)] = filterAdsLogDStream.map(ads_log => {
      // a.取出时间戳
      val timeStamp = ads_log.timeStamp
      // b.格式化为日期字符串
      val dt: String = sdf.format(new Date(timeStamp))

      // c.组合
      ((dt, ads_log.area, ads_log.city, ads_log.adid), 1L) }).reduceByKey(_ + _)

    // 2.将单个批次统计之后的数据集合MySql数据对原有的数据更新
    dateAreaCityAdToCount.foreachRDD(rdd => {
      //对每个分区单独处理
       rdd.foreachPartition(iter => {
         //a.获取连接
         val connection: Connection = JdbcUtil.getConnection
         //b.写库
      iter.foreach { case ((dt, area, city, adid), count) =>
        JdbcUtil.executeUpdate(connection,
          """
            |INSERT INTO area_city_ad_count (dt,area,city,adid,count) |VALUES(?,?,?,?,?) |ON DUPLICATE KEY |UPDATE count=count+?; """.stripMargin, Array(dt, area, city, adid, count, count))
      }
         //c.释放连接
         connection.close() }) }) }
    }
