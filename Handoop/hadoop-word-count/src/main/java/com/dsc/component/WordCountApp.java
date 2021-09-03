package com.dsc.component;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

/**
 *组装罪业，并提交到集群
 * @Author:estic
 * @Date: 2021/9/3 16:10
 */
public class WordCountApp {

    /**为了直观显示参数，使用硬编码，实际开发中可以通过外部传参**/

    private static final String HDFS_URL="hdfs://192.168.0.107:8020";
    private static final String HADOOP_USER_NAME = "root";

    public static void main(String[] args) throws IOException {

        /**文件输入路径和输出路径由外部传参指定**/
        if (args.length<2){

            System.out.println("Input and output paths are necessary!");
            return;

        }
        // 需要指明hadoop用户名，否则在HDFS上创建目录时可能会抛出权限不足的异常
        System.setProperty("HADOOP_USER_NAME", HADOOP_USER_NAME);

        Configuration configuration = new Configuration();
        // 指明HDFS的地址
        configuration.set("fs.defaultFS", HDFS_URL);

        // 创建一个Job
        Job job = Job.getInstance(configuration);

        //设置运行的主类
        job.setMapperClass(WordCountMapper.class);

        //设置Mapper和Reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
    }
}
