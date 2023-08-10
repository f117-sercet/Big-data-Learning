package com.hadoop.mapReduce.FlowBeanCount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.yarn.webapp.hamlet2.HamletSpec;

import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/10 9:55
 */
public class FlowMapper  extends Mapper<LongWritable, Text, Text, FlowBean> {

    private Text outK = new Text();
    private FlowBean outV = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //super.map(key, value, context);

        // 1.获取一行数据,且切割数据,获取手机号，上行流量，下行流量
        String[] split = value.toString().split("\t");
        String phone = split[1];
        String up = split[split.length - 3];
        String down = split[split.length - 2];


        //2.封装 outK,outV
        outK.set(phone);
        outV.setUpFlow(Long.parseLong(up));
        outV.setDownFlow(Long.parseLong(down));
        outV.setSumFlow();





    }
}
