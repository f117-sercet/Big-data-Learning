package com.dsc;

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
import java.net.URISyntaxException;

/**
 *组装罪业，并提交到集群
 * @Author:estic
 * @Date: 2021/9/3 16:10
 */
public class WordCountApp {

    /**为了直观显示参数，使用硬编码，实际开发中可以通过外部传参**/

    private static final String HDFS_URL="hdfs://192.168.0.107:8020";
    private static final String HADOOP_USER_NAME = "root";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ClassNotFoundException {

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

        //设置Mapper输出key和value的类型

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置Reducer输出key和value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //如果输出目录已经存在，则必须先删除，否则重复运行程序时会抛出异常
        FileSystem fileSystem = FileSystem.get(new URI(HDFS_URL),configuration,HADOOP_USER_NAME);
        Path outputPath = new Path(args[1]);
        if (fileSystem.exists(outputPath)){
            fileSystem.delete(outputPath,true);
        }

        //设置作业输入文件路径和输出文件的路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,outputPath);

        // 将作业提交到群集并等待它完成，参数设置为true代表打印显示对应的进度
        boolean result = job.waitForCompletion(true);

        //关闭之前创建的fileSystem
        fileSystem.close();

        //根据作业结果，终止当前运行的Java虚拟机，退出程序
        System.exit(result? 0:-1);


    }
}
