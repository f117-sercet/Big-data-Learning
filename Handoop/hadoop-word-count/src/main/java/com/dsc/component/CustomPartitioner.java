package com.dsc.component;

import com.dsc.utils.WordCountDataUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 按照单词分区
 * @Author:estic
 * @Date: 2021/9/6 11:09
 */
public class CustomPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        return WordCountDataUtils.WORD_LIST.indexOf(text.toString());
    }
}
