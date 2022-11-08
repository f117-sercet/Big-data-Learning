import scala.language.postfixOps

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/8 15:50
 */
object Collection {

  // 创建map类型数据集合
  val mapStream = env.fromElements(Map("name" -> "Peter", "age" -> 18), Map("name" -> "Linda", "age" -> 25))

  // 创建List 类型数据结构集
  val listStream = env.fromElements(List(1, 2, 3, 4, 5, 6), List(2, 4, 3, 2))

  // 定义Type Hint 输出类型参数
  DataStream < Integer > typeStream = input
    .flatMap(new MyMapFunction < String, Integer())
    .returns(new TypeHint < Integer > ()
    ) // 通过returns 方法指定返回指定参数类型
};

