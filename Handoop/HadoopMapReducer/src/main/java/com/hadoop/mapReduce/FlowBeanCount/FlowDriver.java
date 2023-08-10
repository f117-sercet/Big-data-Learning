package com.hadoop.mapReduce.FlowBeanCount;

import com.sun.deploy.uitoolkit.impl.fx.AppletStageManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.sql.SQLOutput;


/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/10 10:12
 */
public class FlowDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        //获取 Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //关联 本Dirver
        job.setJarByClass(FlowDriver.class);

        // 3.关联Mapper和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 4. 设置 Map 段输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);


        //5.设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 6.设置程序的输入输出路径
        FileInputFormat.setInputPaths(job, new Path("C:\\Users\\LENOVO\\Desktop\\inputflow"));
        FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\LENOVO\\Desktop\\folwBeanOut"));

        // 提加Job
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);


    }
}
