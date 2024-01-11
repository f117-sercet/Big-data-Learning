package com.kafkaTest.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Description： 自定义分区
 *
 * @author: 段世超
 * @aate: Created in 2024/1/11 17:54
 */
public class MyPartitioner implements Partitioner {

    @Override
    public int partition(String s, Object key, byte[] keyBytes, Object value, byte[] bytes1, Cluster cluster) {

        // 获取数据
        String msgValues = value.toString();

        int partition = 0;

        if (msgValues.contains("atguigu")) {

            partition = 0;
        }else {
            partition = 1;
        }

        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
