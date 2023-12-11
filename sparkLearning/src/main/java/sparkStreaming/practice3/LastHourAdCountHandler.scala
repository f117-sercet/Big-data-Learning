package sparkStreaming.practice3

import org.apache.spark.streaming.Minutes
import org.apache.spark.streaming.dstream.DStream
import sparkStreaming.practice.Spark01Adverties

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/11 8:46
 */
object LastHourAdCountHandler {

  // 时间格式化对象
  private val sdf: SimpleDateFormat = new SimpleDateFormat("HH:mm")

  /**
   * 统计最近一小时广告分时点击总数
   *
   * @param filterAdsLogDStream
   * @return
   */
  def getAdHourMintToCount(filterAdsLogDStream: DStream[Spark01Adverties]): DStream[(String, List[(String, Long)])] = {

    // 1.开窗 => 时间间隔为一个小时 window()
    val windowAdsLogDStream: DStream[Spark01Adverties] = filterAdsLogDStream.window(Minutes(2))

    //2.转换数据结构 ads_log =>((adid,hm),1L) map()
     val adHmToOneDStream: DStream[((String, String), Long)] =
       windowAdsLogDStream.map(adsLog => {
        val timestamp: Long = adsLog.timeStamp
       val hm: String = sdf.format(new Date(timestamp))
    ((adsLog.adid, hm), 1L)
  })
  // 3.统计总数
  val adHmToCountDStream: DStream[((String, String), Long)] = adHmToOneDStream.reduceByKey(_ + _)

  // 4.转换数据结构
  val adToHmCountDStream: DStream[(String, (String, Long))]
      = adHmToCountDStream.map {
    case ((adid, hm), count) => (adid, (hm, count))
  }
    //5.按照 adid 分组 (adid,(hm,sum))=>(adid,Iter[(hm,sum),...]) groupByKey
    adToHmCountDStream.groupByKey() .mapValues(iter =>
      iter.toList.sortWith(_._1 < _._1)
    )
  }
}
