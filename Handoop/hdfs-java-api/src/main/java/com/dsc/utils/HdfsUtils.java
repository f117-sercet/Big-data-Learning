package com.dsc.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    /**
     * 拷贝文件到HDFS
     * @param localpath
     * @param hdfsPath
     * @throws IOException
     */
    public void copyFromLocalFile(String localpath,String hdfsPath) throws IOException {

        fileSystem.copyFromLocalFile(new Path(localpath),new Path(hdfsPath));

    }

    /**
     * 从HDFS下载文件
     *
     * @param hdfsPath  文件在hdfs上的路径
     * @param localPath 存储到本地的路径
     */
    public void copyToLocalFile(String hdfsPath, String localPath) throws Exception {
        fileSystem.copyToLocalFile(new Path(hdfsPath), new Path(localPath));
    }
    /**
     * 查询给定路径中文件/目录的状态
     *
     * @param path 目录路径
     * @return 文件信息的数组
     */
    public FileStatus[] listFiles(String path) throws Exception {
        return fileSystem.listStatus(new Path(path));
    }


    /**
     * 查询给定路径中文件的状态和块位置
     *
     * @param path 路径可以是目录路径也可以是文件路径
     * @return 文件信息的数组
     */
    public RemoteIterator<LocatedFileStatus> listFilesRecursive(String path, boolean recursive) throws Exception {
        return fileSystem.listFiles(new Path(path), recursive);
    }


    /**
     * 查看文件块信息
     *
     * @param path 文件路径
     * @return 块信息数组
     */
    public BlockLocation[] getFileBlockLocations(String path) throws Exception {
        FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
        return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 删除是否成功
     */
    public boolean delete(String path) throws Exception {
        return fileSystem.delete(new Path(path), true);
    }


    /**
     * 把输入流转换为指定字符
     *
     * @param inputStream 输入流
     * @param encode      指定编码类型
     */
    private static String inputStreamToString(InputStream inputStream, String encode) {
        try {
            if (encode == null || ("".equals(encode))) {
                encode = "utf-8";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encode));
            StringBuilder builder = new StringBuilder();
            String str = "";
            while ((str = reader.readLine()) != null) {
                builder.append(str).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
