package com.dsc.component;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @Author:estic
 * @Date: 2021/9/3 16:34
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
}
