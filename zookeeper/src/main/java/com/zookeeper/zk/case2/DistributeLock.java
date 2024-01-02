package com.zookeeper.zk.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/2 11:46
 */
public class DistributeLock {

    private String connectString="hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private int  sessionTimeout = 2000;
    private ZooKeeper zk;

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    public DistributeLock() throws Exception {

        // 获取连接
        zk = new ZooKeeper(connectString,sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
        //阻塞
        countDownLatch.await();

        // 判断根节点是否存在

        Stat exists = zk.exists("/locks", false);
        if (exists == null) {

            // 创建根节点
            zk.create("/locks","locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        }
    }

    // 枷锁
    public void zklock(){

        // 创建对应的节点

        // 判断创建的节点是否为最小的序号节点，是，获取倒锁，否，监听前一个节点

    }

    // 解锁
    public void zkunlock(){

        // 删除节点
    }
}
