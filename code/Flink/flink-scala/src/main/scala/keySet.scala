/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/3 18:25
 */
object keySet {

   val dataStream :DataStream[(String,Int)] = env.fromElements(("a",1),("c",2));

  //根据第一个字段重新分区,然后对第二个字段进行求和运算
  val result = dataStream.keyBy(0).sum(1)

   /***********聚合*****************/

   val dataSet = env.fromElements(("hello",1,),("flink",3))

   //根据第一个字段进行数据重分区
   val groupedDataSet:GroupedDataset[(String,Int)] = dataSet.gropuBy(0)

    // 求取相同Key值下第二个字段的最大值
    groupedDataSet.max(1)
}
