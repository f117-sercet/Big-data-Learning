package com.zookeeper.zk.case1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/2 10:16
 */
public class DistributeClient {

    String connectionString="hadoop102:2181,hadoop103:2181,hadoop104:2181";
    int sessionTimeout =2000;
    private ZooKeeper zk;
    public static void main(String[] args) throws Exception {

        // 获取zk连接
        DistributeClient client = new DistributeClient();
        client.getConnection();
        // 监听路径节点变化
        client.getServerList();
        // 业务逻辑
        Thread.sleep(1000);
    }

    private void getServerList() throws InterruptedException, KeeperException {

        ArrayList<Object> servers = new ArrayList<>();
        List<String> children = zk.getChildren("/serveres", true);
        for (String child : children) {
            byte[] data = zk.getData("/serveres/" + child, false, null);

            servers.add(new String(data));
        }

        // 打印
        System.out.println(servers);
    }

    private void getConnection() throws Exception {

        zk = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                try {
                    getServerList();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
