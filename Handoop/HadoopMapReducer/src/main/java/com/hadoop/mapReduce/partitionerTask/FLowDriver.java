package com.hadoop.mapReduce.partitionerTask;

import com.hadoop.mapReduce.FlowBeanCount.FlowMapper;
import com.hadoop.mapReduce.FlowBeanCount.FlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/30 17:19
 */
public class FLowDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 获取Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 关联本机Driver类
        job.setJarByClass(FLowDriver.class);

        // 关联Mapper和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);


        // 设置Map端输出数据的kv类型
        job.setMapOutputValueClass(Text.class);

        //8 指定自定义分区器
        job.setPartitionerClass(ProvincePartitioner.class);

        //9 同时指定相应数量的ReduceTask
        job.setNumReduceTasks(5);

        //6 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\inputflow"));
        FileOutputFormat.setOutputPath(job, new Path("D\\partitionout"));

        //7 提交Job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);

    }
}
