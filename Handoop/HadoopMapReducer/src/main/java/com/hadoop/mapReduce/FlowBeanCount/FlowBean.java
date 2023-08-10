package com.hadoop.mapReduce.FlowBeanCount;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/10 9:42
 */
public class FlowBean implements Writable {

    private long upFlow; // 上行流量
    private long downFlow; //下行流量

    private long sumFlow; // 总流量

    public FlowBean(long upFlow, long downFlow, long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public FlowBean() {
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow() {
        this.sumFlow = this.upFlow+this.downFlow;
    }

    // 实现序列化和反序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {

        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);


    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();

    }


    // 重写toString
    @Override
    public String toString() {

        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }
}
