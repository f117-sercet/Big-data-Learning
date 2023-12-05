package sparkStreaming.practice

import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/12/5 8:34
 */
case class Spark01Adverties(
                           timeStamp:Long,
                           area:String,
                           city: String,
                           userid: String,
                           adid: String ) {



}
