package com.dsc.component;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @Author:estic
 * @Date: 2021/9/3 16:35
 */
public class WordCountReducer  extends Reducer<Text, IntWritable, Text, IntWritable> {
}
