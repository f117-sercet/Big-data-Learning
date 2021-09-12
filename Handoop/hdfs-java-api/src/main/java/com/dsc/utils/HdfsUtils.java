package com.dsc.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 工具类
 * @Author:estic
 * @Date: 2021/9/10 15:20
 */
public class HdfsUtils {

    private static final String HDFS_PATH = "hdfs://192.168.0.107:8020";
    private static final String HDFS_USER = "root";
    private static FileSystem fileSystem;

static {
    Configuration configuration = new Configuration();
    configuration.set("dfs.replication","1");
    try {
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, HDFS_USER);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }

}

    public static FileSystem getFileSystem() {
        return fileSystem;
    }


    /**
     * 创建目录 支持递归创建
     *
     * @param path 路径地址
     * @return 创建是否成功
     */

    public static boolean mkdir(String path) throws IOException {

        return fileSystem.mkdirs(new Path(path));
    }

    /**
     * 创建文件并写入内容
     * @param path
     * @param context
     */
    public  void createAndWrite(String path,String context) throws IOException {

        FSDataOutputStream out = fileSystem.create(new Path(path));
        out.write(context.getBytes());
        out.flush();
        out.close();
    }

}