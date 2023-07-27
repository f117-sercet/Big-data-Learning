package com.atguigu.hadoop.hdfsClient;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.net.URI;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/7/27 18:11
 */
public class hdfsClient {

    @Test
    public void testMkdirs() throws Exception{

        // 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 创建目录
       fs.mkdirs(new Path("/xiyou/huaguoshan"));

       // 关闭资源
        fs.close();
    }
}
