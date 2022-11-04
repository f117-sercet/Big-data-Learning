/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/4 15:52
 */
class test02 {

  val dataSet = env.fromElements(("hello",1),("flink",2))
  // 根据第一个字段进行数据重新分区
  val groupedDataSet:GroupedDataset[(String,String)] = dataSet.groupBy(0)
  // 求取相同key值下第二个字段的最大值
  groupedDataSet.max(1)


  val intStream:dataStream[Int] = env.fromElements(3,1,2,1,5)
  //创建String类型的数据结构
  val dataStream:DataStream[String] = env.fromElements("hello","flink")



}
