package sparkStreaming.practice

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import sparkStreaming.util.PropertiesUtil

import java.util.{Properties, Random}
import scala.collection.mutable.ArrayBuffer

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/4 10:04
 */
object MockerRealTime {

  /*****
   * 模拟的数据
   */

 def generateMockData():Array[String] = {
   val array:ArrayBuffer[String] = ArrayBuffer[String]()
   val CityRandomOpt = RandomOptions(RanOpt(CityInfo(1, "北京", "华北"), 30),
     RanOpt(CityInfo(2, "上海", "华东"), 30),
     RanOpt(CityInfo(3, "广州", "华南"), 10),
     RanOpt(CityInfo(4, "深圳", "华南"), 20),
     RanOpt(CityInfo(5, "天津", "华北"), 10))

   val random = new Random()
     for(i <- 0 to 50){
       val timestamp: Long = System.currentTimeMillis();
       val cityInfo: CityInfo = CityRandomOpt.getRandomOpt
       val city: String = cityInfo.city_name
       val area: String = cityInfo.area
       val adid: Int = 1 + random.nextInt(6)
       val userid: Int = 1 + random.nextInt(6)

       // 拼接实时数据
       array += timestamp + " " + area + " " + city + " " + userid + " " + adid
     }
   array.toArray
 }
  def createKakaProducer(broker:String):KafkaProducer[String,String] = {

    // 创建配置对象
    val prop = new Properties()
    // 添加配置
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
    prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    // 根据配置创建kafka生产者
    new KafkaProducer[String,String](prop)

  }

  def main(args: Array[String]): Unit = {

    // 获取配置文件中的参数
    val config: Properties = PropertiesUtil.load("config.properties")
    val broker: String = config.getProperty("kafka.broker.list")
    val topic: String = "test"

     // 创建kafka消费者
     val kafkaProducer:KafkaProducer[String,String] = createKakaProducer(broker)

    while (true) {
      // 随机产生实时数据并通过kafka生产者发送到kafka集群中
      for(line <- generateMockData()){
        kafkaProducer.send(new ProducerRecord[String,String](topic,line))
        println(line)
      }
      Thread.sleep(2000)
    }
  }

}
