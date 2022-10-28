package com.dsc;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import static org.apache.flink.api.java.ExecutionEnvironment.getExecutionEnvironment;

/**
 * @Author:estic
 * @Date: 2021/8/30 15:06
 */
public class StreamingJob {
    private static final String ROOT_PATH = "D:\\BigData-Notes\\code\\Flink\\flink-basis-java\\src\\main\\resources\\";

    public static void main(String[] args) throws Exception {


        StreamExecutionEnvironment env = null;
        StreamExecutionEnvironment StreamutionEnvironment = env.getExecutionEnvironment();
        DataStreamSource<String> streamSource = env.readTextFile(ROOT_PATH + "log4j.properties");
        streamSource.writeAsText(ROOT_PATH + "out").setParallelism(1);
        env.execute();

    }
}
