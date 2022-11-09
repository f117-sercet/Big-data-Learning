import akka.routing.Broadcast
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction
import org.apache.flink.streaming.api.scala.BroadcastConnectedStream
import org.apache.flink.util.Collector

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 16:03
 */
class test03 {

  val resultStream2 = connectedStream.flatMp(new CoFlatMapFunction[(String,Int),Int,(String,Int,Int)] {

     // 定义共享变量
    val number = 0

    // 定义第一个数据集处理函数
    override def flatMap1(in1: (String, Int), out: Collector[(String, Int, Int)]): Unit = {

      collection.collect((in1._1, in1._2,number))
    }

    // 定义第二个 数据集处理函数
    override def flatMap2(in2: Int, out: Collector[(String, Int, Int)]): Unit = {

          number = in2
    }
  })

    // 通过keyBy 函数指定的key连接两个数据集
    val keydConnect:ConnectedStreams[(String, Int),Int] = dataStream1.connect(dataStream2).keyBy(1,0)
    // 通过broadcast关联两个数据集
    val broadcastConnect:BroadcastConnectedStream[(String,Int),Int] = dataStream1.connect(dataStream2.broadcast())

}
