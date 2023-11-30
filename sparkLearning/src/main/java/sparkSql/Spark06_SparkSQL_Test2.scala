package sparkSql

import org.apache.commons.collections.Buffer
import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/30 10:59
 */
object Spark06_SparkSQL_Test2 {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "atguigu")
    // TODO 创建SparkSQL的运行环境
    val spark = SparkSession.builder()
      .config("spark.sql.warehouse.dir", "hdfs://hadoop102:8020/user/hive/warehouse")
      .enableHiveSupport()
      .master("local[*]")
      .appName("sql")
      .getOrCreate()

    val df = spark.sql("use sparklearning").toDF()

    val t1 = spark.sql(
      """
        |  select
        |     a.*,
        |     p.product_name,
        |     c.area,
        |     c.city_name
        |  from user_visit_action a
        |  join product_info p on a.click_product_id = p.product_id
        |  join city_info c on a.city_id = c.city_id
        |  where a.click_product_id > -1
    """.stripMargin).createOrReplaceTempView("t1")

    // 根据区域，商品进行数据聚合
    spark.udf.register("cityRemark", functions.udaf(new CityRemarkUDAF()))

    // 根据区域，商品进行数据聚合
    val t2 = spark.sql(
      """
        |  select
        |     area,
        |     product_name,
        |     count(*) as clickCnt,
        |     cityRemark(city_name) as city_remark
        |  from t1 group by area, product_name
        """.stripMargin).createOrReplaceTempView("t2")

    // 区域内对点击数量进行排行
    val t3 = spark.sql(
      """
        |  select
        |      *,
        |      rank() over( partition by area order by clickCnt desc ) as rank
        |  from t2
      """.stripMargin).createOrReplaceTempView("t3")

    // 取前3名
    spark.sql(
      """
        | select
        |     *
        | from t3 where rank > 3 order by rank desc
        """.stripMargin).show(false)

    spark.close()
  }

  case class Buffer(var total: Long, var cityMap: mutable.Map[String, Long])

  // 自定义聚合函数：实现城市备注功能
  // 1. 继承Aggregator, 定义泛型
  //    IN ： 城市名称
  //    BUF : Buffer =>【总点击数量，Map[（city, cnt）, (city, cnt)]】
  //    OUT : 备注信息
  class CityRemarkUDAF() extends Aggregator[String, Buffer, String] {
    override def zero: Buffer = {

      // 缓冲区
      Buffer(0, mutable.Map[String, Long]())
    }


    // 更新缓冲区数据
   override def reduce(buffer: Buffer, city: String): Buffer = {

      buffer.total += 1
     // 缓冲区加一
      val newCount = buffer.cityMap.getOrElse(city, 0L) + 1
      buffer.cityMap.update(city, newCount)
      buffer
    }

    // 合并缓冲区
    override def merge(buff1: Buffer, buff2: Buffer): Buffer = {

      buff1.total +=buff2.total
      val map1 = buff1.cityMap
      val map2 = buff2.cityMap
      map2.foreach {
        case (city, cnt) => {

          val newCount = map1.getOrElse(city, 0L) + cnt
          map1.update(city, newCount)
        }
      }
      buff1.cityMap = map1
      buff1

    }

    override def finish(buff: Buffer): String = {

      val remarkList = ListBuffer[String]()

      val totalcnt = buff.total
      val cityMap = buff.cityMap

      // 降序排列
      val cityCntList = cityMap.toList.sortWith(
        (left, right) => {
          left._2 > right._2
        }
      ).take(2)

      val hasMore = cityMap.size > 2
      var rsum = 0L
      cityCntList.foreach {
        case (city, cnt) => {
          val r = cnt * 100 / totalcnt
          remarkList.append(s"${city} ${r}%")
          rsum += r
        }
      }
      if (hasMore) {
        remarkList.append(s"其他 ${100 - rsum}%")
      }

      remarkList.mkString(", ")
    }

    override def bufferEncoder: Encoder[Buffer] =Encoders.product

    override def outputEncoder: Encoder[String] = Encoders.STRING
  }
}
