package com.hadoop.mapReduce.reduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/6 18:13
 */
public class TableMapper extends Mapper<LongWritable,Text, Text,TableBean> {

    private String filename;
    private Text outK = new Text();
    private TableBean outV = new TableBean();

}
