package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() throws Exception {

        //连接的集群nn地址
        URI url = new URI("hdfs://hadoop102:8020");

        //创建一个配置文件
        Configuration configuration = new Configuration();

        configuration.set("dfs.replication","2");

        //用户
        String user = "atguigu";

        //1.获取到客户端对象
        fs = FileSystem.get(url,configuration,user);
    }

    public void close() throws Exception {

        //关闭资源
        fs.close();
    }

    //创建目录
    @Test
    public void testmkdir() throws IOException {

        //创建文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan1"));
    }
}
