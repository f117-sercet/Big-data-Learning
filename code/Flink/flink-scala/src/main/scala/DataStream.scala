import org.apache.flink.shaded.guava18.com.google.common.base.Objects

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 15:05
 */
class DataStream {

  // 读取文本文件
  val textStream = env.readTextFile("/user/local/data_example.log")
  // 通过指定CSVInputFormat读取文件
  val csvStream = env.readFile(new CSVInputFormat[String](new Path("/user/local/data_example.cs")){

    override  def  fillRecord(out: String,objects: Array[AnyRef]):String = {
      return null
    }
  },"/user/local/data_example.csv")

  // Socket  数据源
  val socketStream = env.socketStream("localhost",9999)

}
