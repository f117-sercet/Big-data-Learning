package com.hadoop.mapReduce.partitionerTask;

import com.hadoop.mapReduce.FlowBeanCount.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/30 17:14
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {

    // 获取手机号前三位
        String phone = text.toString();
        String prePhone = phone.substring(0, 3);

        //定义一个分区号变量partition,根据prePhone设置分区号
        int partition;

        if("136".equals(prePhone)){
            partition = 0;
        }else if("137".equals(prePhone)){
            partition = 1;
        }else if("138".equals(prePhone)){
            partition = 2;
        }else if("139".equals(prePhone)){
            partition = 3;
        }else {
            partition = 4;
        }

        //最后返回分区号partition
        return partition;

    }
}
