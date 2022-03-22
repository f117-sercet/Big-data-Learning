package com.atguigu.hdfs;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
   //上传

    /**
     * 参数优先级
     *      * hdfs-default.xml => hdfs-site.xml=> 在项目资源目录下的配置文件 =》代码里面的配置
     */
    @Test
    public void testPut() throws IOException {

        FSDataOutputStream fos = fs.create(new Path("/input"));
        fos.write("你好".getBytes());
    }
    @Test
    public void testPut2() throws IOException {
        FSDataOutputStream fos = fs.create(new Path("/input"));

        fos.write("hello world".getBytes());
    }

    // 文件下载
    @Test
    public void testGet() throws IOException {
        // 参数的解读：参数一：原文件是否删除；参数二：原文件路径HDFS； 参数三：目标地址路径Win ; 参数四：
        //fs.copyToLocalFile(true, new Path("hdfs://hadoop102/xiyou/huaguoshan/"), new Path("D:\\"), true);
        fs.copyToLocalFile(false, new Path("hdfs://hadoop102/a.txt"), new Path("D:\\"), false);
    }

    //删除
    @Test
    public void testRm() throws IOException {
        // 参数解读：参数1：要删除的路径； 参数2 ： 是否递归删除
        // 删除文件
        //fs.delete(new Path("/jdk-8u212-linux-x64.tar.gz"),false);

        // 删除空目录
        //fs.delete(new Path("/xiyou"), false);

        // 删除非空目录
        fs.delete(new Path("/jinguo"), true);
    }

    //文件的更名和移动
    @Test
    public void testmv() throws IOException {

        // 参数解读：参数1 ：原文件路径； 参数2 ：目标文件路径
        // 对文件名称的修改
        //fs.rename(new Path("/input/word.txt"), new Path("/input/ss.txt"));

        // 文件的移动和更名
        //fs.rename(new Path("/input/ss.txt"),new Path("/cls.txt"));

        // 目录更名
        fs.rename(new Path("/input"),new Path("/output"));
    }

    //获取文件详细信息
     @Test
    public void fileDetail() throws IOException {

         //获取文件信息
         RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

         //    sql="select CLOSE_COST_ORDER as settlementId,UPDATED_DT as settlementDate,CASH as settlementAmt,PURPOSE as purpose,RECIPIENT_COMPANY_ACCOUNT as incomeBankAccount,RECIPIENT_COMPANY_NAME as incomeName, RECIPIENT_COMPANY_BANK as incomeBank,PAY_COMPANY_ACCOUNT as paymentBankAccount, PAY_COMPANY_NAME as paymentName, PAY_COMPANY_BANK as paymentBank , CREATED_BY as createUserName, UPDATED_BY as checkedUserName,'SKGL' as MODULE_TYPE  from JSZX_RECIPIENT_MANAGER where CLOSE_COST_ORDER=:CLOSE_COST_ORDER";bi

         //遍历文件
         while (listFiles.hasNext()) {

             LocatedFileStatus fileStatus = listFiles.next();

             System.out.println("===============" + fileStatus.getPath() + "====");
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
     }


}
