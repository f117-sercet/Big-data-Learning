package sparkSql

import org.apache.spark.sql.SparkSession

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/30 10:48
 */
object Spark06_SparkSql {

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

    spark.sql(
      """
        |select
        |    *
        |from (
        |    select
        |        *,
        |        rank() over( partition by area order by clickCnt desc ) as rank
        |    from (
        |        select
        |           area,
        |           product_name,
        |           count(*) as clickCnt
        |        from (
        |            select
        |               a.*,
        |               p.product_name,
        |               c.area,
        |               c.city_name
        |            from user_visit_action a
        |            join product_info p on a.click_product_id = p.product_id
        |            join city_info c on a.city_id = c.city_id
        |            where a.click_product_id > -1
        |        ) t1 group by area, product_name
        |    ) t2
        |) t3 where rank <= 3
    """.stripMargin).show()
    spark.stop()
  }

}
