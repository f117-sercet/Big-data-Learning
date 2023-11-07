package com.hadoop.mapReduce.mapJoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/7 9:24
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Map<String, String> pdMap = new HashMap<>();
    private Text text = new Text();

    // 任务开始前将pd数据缓存进入pdMap


    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {

        // 通过缓存文件得到小表述
        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);

        // 获取文件系统对象,并开流动
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fs.open(path);

        // 通过包装流转换为reader,方便按行读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));

        // 逐行读取,按行处理
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            // 切割一行业
            String[] split = line.split("\t");
            pdMap.put(split[0],split[1]);
        }

        // 关流

        IOUtils.closeStream(reader);

    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {

        // 读取大表数据
        String[] fields = value.toString().split("\t");

        //通过大表每行数据的pid,去pdMap里面取出pname
        String pname = pdMap.get(fields[1]);

        //将大表每行数据的pid替换为pname
        text.set(fields[0] + "\t" + pname + "\t" + fields[2]);

        // 写出
        context.write(text,NullWritable.get());
    }
}
