/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 15:16
 */
class CollectionStream {

    // 通过fromElements 从元素集合中创建DataStream 数据集
   val dataStream = env.fromElements(Tuple2(1L,3L),Tuple2(1L,2L),
    Tuple2(1L,3L),Tuple2(1L,2L))

}
