package com.hadoop.mapReduce.writableCompable;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/2 16:28
 */
public class FlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        // 获取Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 关联本地Driver
        job.setJarByClass(FlowDriver.class);

        // 关联Mapper和Reducer
        job.setReducerClass(FlowReduce.class);

        // 设置Map端输出数据的kv类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        //6 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\inputflow2"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\comparout"));


        // 提交
        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);
    }
}
