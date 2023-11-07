package com.hadoop.mapReduce.reduceJoin;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/7 9:04
 */
public class TableDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {


        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(TableDriver.class);
        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path("D:\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\output"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);


    }
}
