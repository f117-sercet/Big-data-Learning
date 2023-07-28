package com.atguigu.hadoop.hdfsClient;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/7/27 18:11
 */
public class hdfsClient {

    @Test
    public void testMkdirs() throws Exception {

        // 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan"));

        // 关闭资源
        fs.close();
    }
    @Test
    public void testCopyFromLocalFile() throws Exception{
        // 1.获取文件系统
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication","2");
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 上传文件
        fs.copyFromLocalFile(new Path("d://hello.txt"),new Path("/xiyou/huaguoshan"));

    }

    @Test
    public void testCopyToLocalFile() throws Exception {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 2 执行下载操作
        // boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path("/xiyou/huaguoshan/hello.txt"), new Path("d:/hello2.txt"), true);

        // 3 关闭资源
        fs.close();
    }

    // 文件更名与移动
    @Test
    public void testRename() throws IOException, InterruptedException, URISyntaxException{

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 2 修改文件名称
        fs.rename(new Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("/xiyou/huaguoshan/meihouwang.txt"));

        // 3 关闭资源
        fs.close();
    }

    // 删除文件和目录
    @Test
    public void testDelete() throws IOException, InterruptedException, URISyntaxException{

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 2 执行删除
        fs.delete(new Path("/xiyou"), true);

        // 3 关闭资源
        fs.close();
    }

    // 文件查看
    @Test
    public void testListFiles() throws Exception {

        // 1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println("========" + fileStatus.getPath() + "=========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());

            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
        // 3 关闭资源
        fs.close();
    }


      // HDFS文件和文件夹判断
      @Test
      public void testListStatus() throws IOException, InterruptedException, URISyntaxException{

          // 1 获取文件配置信息
          Configuration configuration = new Configuration();
          FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

          // 2 判断是文件还是文件夹
          FileStatus[] listStatus = fs.listStatus(new Path("/"));

          for (FileStatus fileStatus : listStatus) {

              // 如果是文件
              if (fileStatus.isFile()) {
                  System.out.println("f:"+fileStatus.getPath().getName());
              }else {
                  System.out.println("d:"+fileStatus.getPath().getName());
              }
          }

          // 3 关闭资源
          fs.close();
      }


}
