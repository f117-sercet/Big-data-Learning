package com.hadoop.mapReduce.FlowBeanCount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/10 10:03
 */
public class FlowReducer extends Reducer <Text, FlowBean, Text, FlowBean> {

    private FlowBean outV = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        long totalUp = 0;
        long totalDown = 0;

        // 遍历values，将其中的上行流量，下行流量分别累加
        for (FlowBean flowBean : values) {
            totalUp+=flowBean.getUpFlow();
            totalDown+=flowBean.getDownFlow();
        }
        //2.封装 outKV
        outV.setUpFlow(totalUp);
        outV.setDownFlow(totalDown);
        outV.setSumFlow();

        // 3.写出outK outV
        context.write(key,outV);
    }
}
