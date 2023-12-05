package sparkStreaming.practice

import org.apache.spark.streaming.dstream.DStream
import org.joda.time.format.ISODateTimeFormat.date
import sparkStreaming.practice.util.JdbcUtil

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/5 16:21
 */
object BlackListHandler {

  // 格式化对象
  private val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def addBlackList(filterAdsLogDStream:DStream[Spark01Adverties]):Unit ={

    // 统计当前 批次中单日每个用户每个广告的总次数
    //1.将数据接转换结构 ads_log=>((date,user,adid),1)
    val dateUserAdToOne: DStream[((String, String, String), Long)] = filterAdsLogDStream.map(adsLog => {
      // a.将时间戳转换为 日期字符串
      val date = sdf.format(new Date(adsLog.timeStamp))
      // 返回值
      ((date,adsLog.userid,adsLog.adid),1L)
    })

    // 2统计单日每个用户点击广告的总次数
    //((date,user,adid),1L) => ((date,user,adid),count)
    val dateUserAToCount = dateUserAdToOne.reduceByKey(_ + _)
     dateUserAToCount.foreachRDD(rdd =>{
       rdd.foreachPartition(iter =>{
         val connection = JdbcUtil.getConnection
         iter.foreach { case ((dt, user, ad), count) => JdbcUtil.executeUpdate(connection,
           """
             |INSERT INTO user_ad_count (dt,userid,adid,count) |VALUES (?,?,?,?) |ON DUPLICATE KEY |UPDATE count=count+?
       """.stripMargin, Array(dt, user, ad, count, count))
           val ct: Long = JdbcUtil.getDataFromMysql(connection, "select count from user_ad_count where dt=? and userid=? and adid =?", Array(dt, user, ad))
       if (ct>30) {
         JdbcUtil.executeUpdate(connection, "INSERT INTO black_list (userid) VALUES (?) ON DUPLICATE KEY update userid=?", Array(user, user))
       }
         }
         connection.close()
         })
       })
  }

  def filterByBlackList(adsLogDStream: DStream[Spark01Adverties]): DStream[Spark01Adverties] = {
    adsLogDStream.transform(
      rdd =>
      {
        rdd.filter(adsLog => {
          val connection: Connection = JdbcUtil.getConnection
          val bool: Boolean = JdbcUtil.isExist(connection, "select * from black_list where userid=?", Array(adsLog.userid))
          connection.close()
          ! bool
        })
      })
  }
}
