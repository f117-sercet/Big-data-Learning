package sparkStreaming

import com.nimbusds.jose.util.StandardCharset
import jline.internal.InputStreamReader
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

import java.io.BufferedReader
import java.net.Socket

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/1 8:37
 */

  class CustomerReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {

    //启动时,调用该方法，读取数据并将数据发送给Spark
    override def onStart(): Unit = {
      new Thread("Socket Receiver") {


        override def run() {
          receive()
        }
      }.start()
    }

    def receive(): Unit = {

      //创建Socket
      val socket = new Socket(host, port)

      // 定义一个变量，接收端口传过来的数据
      var  input:String = null

      //创建一个 BufferedReader 用于读取端口传来的数据
      val reader = new BufferedReader(new InputStreamReader(socket.getInputStream, StandardCharset.UTF_8))

      // 读取数据
      input = reader.readLine()

      // 当receiver 没有关闭并且输入数据不为空时，则循环发送数据给Spark

      while(!isStopped()&& input!=null){

        store(input)
        input = reader.readLine()

      }
      //跳出循环则关闭资源 reader.close()
      reader.close()
      socket.close()

      // 重启任务
      restart("restart")

    }

    override def onStop(): Unit = {}
}
