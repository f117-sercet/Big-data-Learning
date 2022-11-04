import org.apache.flink.api.common.functions.RichMapFunction

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/4 9:22
 */
case class Person(name: String, age: Int) {

  val person = env.fromElements(Person("hello",1),Person("flink",4))

  // 定义keySelector,实现getKey方法从case class获取Key
  val keyed:keydStream[WC] = person.keyBy(new KeySelector[Person,String](){
   override  def  getKey(Person:Person):String  = person.word
  })

     // 定义匿名实现类实现RichMapFunction接口，完成对字符串到整形数字的转换
     data.map(new RichMapFunction[String,Int] {
       def map(in:String):Int = {in.toInt}
     })

}
