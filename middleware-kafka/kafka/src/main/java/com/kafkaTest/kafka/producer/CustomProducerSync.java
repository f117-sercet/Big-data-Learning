package com.kafkaTest.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/11 17:30
 */
public class CustomProducerSync {

    public static void main(String[] args) throws Exception {
        //0 配置属性
        Properties properties = new Properties();

        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");

        //指定对应的key和value的系列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1.创生产对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 2.发送数据
        for(int i=0; i<5;i++) {

            // 同步
            kafkaProducer.send(new ProducerRecord<>("first", "你好" + i)).get();
        }
        // 3.关闭资源
        kafkaProducer.close();
    }
}
