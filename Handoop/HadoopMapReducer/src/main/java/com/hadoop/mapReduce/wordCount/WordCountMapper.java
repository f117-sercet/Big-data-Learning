package com.hadoop.mapReduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Description： mapreduce 案例
 *
 * @author: 段世超
 * @aate: Created in 2023/8/9 15:44
 */
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    Text k = new Text();

  IntWritable v =  new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        // 获取一行
        String line = value.toString();
        // 切割
        String[] words = line.split(" ");

        // 输出
        for (String word : words) {
            k.set(word);
            context.write(k, v);
        }
    }
}
