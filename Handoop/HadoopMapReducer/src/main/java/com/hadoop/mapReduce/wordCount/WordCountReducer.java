package com.hadoop.mapReduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/9 15:52
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

     int sum;
     IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        // 累加求和
        sum = 0;
        for (IntWritable count : values) {
            sum += count.get();
        }
        // 2.输出
        v.set(sum);
        context.write(key, v);
    }
}
