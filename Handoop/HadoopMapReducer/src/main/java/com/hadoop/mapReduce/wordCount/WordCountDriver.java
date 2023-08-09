package com.hadoop.mapReduce.wordCount;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/9 15:56
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException {

        // 1.获取配置信息以及获取Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.关联本Driver程序的jar
        job.setJarByClass(WordCountDriver.class);

        // 3.关联 Mapper 和 Reducer的Jar
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);


        // 4 设置Mapper输出的kv
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));



    }
}
