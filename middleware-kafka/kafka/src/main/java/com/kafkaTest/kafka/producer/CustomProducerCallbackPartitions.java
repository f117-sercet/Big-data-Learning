package com.kafkaTest.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/9 18:18
 */
public class CustomProducerCallbackPartitions {

    public static void main(String[] args) {
        //0 配置属性
        Properties properties = new Properties();

        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");

        //指定对应的key和value的系列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 关联自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.kafkaTest.kafka.producer.MyPartitioner");


        // 1.创生产对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 2.发送数据
        for(int i=0; i<5;i++) {

            // 异步
            kafkaProducer.send(new ProducerRecord<>("first", 0,"","atguigu" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception==null) {
                        System.out.println("主题："+metadata.topic()+"分区："+metadata.partition());
                    }
                }
            });
        }
        // 3.关闭资源
        kafkaProducer.close();
    }
}
