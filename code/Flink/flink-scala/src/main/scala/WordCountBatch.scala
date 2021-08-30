import org.apache.flink.api.scala.ExecutionEnvironment

/**
 * @Author:estic
 * @Date: 2021/8/30 15:12
 */
object WordCountBatch {

  def main(args: Array[String]): Unit = {

    val benv = ExecutionEnvironment.getExecutionEnvironment
    val dataSet = benv.readTextFile("D:\\BigData-Notes\\code\\Flink\\flink-basis-scala\\src\\main\\resources\\wordcount.txt")
    dataSet.flatMap {
      _.toLowerCase.split(",")
    }
      .filter(_.nonEmpty)
      .map {
        (_, 1)
      }
      .groupBy(0)
      .sum(1)
      .print()
  }
}
