package com.zookeeper.zk.case1;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/2 10:07
 */
public class DistributeServer {

    String connectionString="hadoop102:2181,hadoop103:2181,hadoop104:2181";
    int sessionTimeout =2000;
    private ZooKeeper zk;

    public static void main(String[] args) throws Exception {

        // 获取ck连接
        DistributeServer server = new DistributeServer();
        server.getConnection();
        //2.注册服务器zk集群
        server.regist(args[0]);

        //3.启动业务代码
        server.bussiness();

    }

    private void bussiness() throws Exception {

        Thread.sleep(1000);
    }

    private void regist(String hostname) throws Exception {

        String s = zk.create("/servers/"+hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname+"is online");
    }

   private void getConnection() throws IOException {

        zk = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
               @Override
               public void process(WatchedEvent watchedEvent) {

               }
           });
    }
}
