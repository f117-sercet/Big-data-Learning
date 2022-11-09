import scala.collection.Seq;
import scala.math.Equiv;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 16:21
 */
public class testSplit {

    // 创建数据集
    val dataStream1:DataStream[(String,INT)] = env.fromElements(("a",3),("d",4),("c",2),
            ("c",5),(("a",5))

    // 合并两个数据集
    val splitedStream:SplitStream[(String,INT)] = dataStream1.split(t=>if (t._2 %2 == 0) Seq("even") else
        Seq("odd"));

    // split 函数本身只是对输入数据集进行标记，并没有将数据集真正的切分

    // 筛选偶数数据集
    val evenStream:DataStream[(String,Int)] = splitedStream.select("even")
    //筛选出奇数数据集合
    val oddStream:DataStream[(String,Int)] = splitedStream.select("odd")

    // 筛选出所有数据集
    val allStream:DataStream[(String,Int)] = splitedStream.select("even","odd")
}
