package com.dsc;

import org.apache.flink.api.common.typeinfo.TypeInfo;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2022/11/9 14:50
 */
public class testPojo {

    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 开启Avro序列化方式
        env.getConfig().enableForceAvro();
    }
}
