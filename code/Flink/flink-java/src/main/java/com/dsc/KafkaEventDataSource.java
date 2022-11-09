package com.dsc;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;
import java.util.logging.SimpleFormatter;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 15:24
 */
public class KafkaEventDataSource {

    public static void main(String[] args) throws Exception {

        // properties 参数定义
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Kafka props
        Properties properties = new Properties();
        //指定Kafka的Broker地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.***.***:9092,192.168.***.***:9092,192.168.***.***:9092");
        //指定组ID
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka_test_group1");
        //如果没有记录偏移量，第一次从最开始消费
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 2.从kafka读取数据
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>("kafka_test1", new SimpleStringSchema(), properties);

        DataStreamSource<String> stringDataStreamSource = env.addSource(kafkaSource);

        //3.调用Sink
        stringDataStreamSource.print();

        //4.启动流计算
        env.execute("KafkaSouceReview");
    }
}
