package com.zookeeper.zk.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private String waitPath;

    private CountDownLatch connectLatch = new CountDownLatch(1);

    private CountDownLatch waitLatch = new CountDownLatch(1);
    private String currentMode;

    public DistributeLock() throws Exception {

        // 获取连接
        zk = new ZooKeeper(connectString,sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                // connectlatch,连接zk，释放掉
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    connectLatch.countDown();
                }

                //释放waitLatch 释放
                if (watchedEvent.getType() == Event.EventType.NodeDeleted&& watchedEvent.getPath().equals(waitPath)){

                    waitLatch.countDown();
                }
            }
        });
        //阻塞
        connectLatch.await();//1

        // 判断根节点是否存在

        Stat exists = zk.exists("/locks", false);
        if (exists == null) {

            // 创建根节点
            zk.create("/locks","locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        }
    }

    // 枷锁
    public void zklock() throws Exception {

        // 创建对应的节点
        currentMode = zk.create("/locks/" + "seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        // 判断创建的节点是否为最小的序号节点，是，获取倒锁，否，监听前一个节点
        List<String> children = zk.getChildren("/locks", false);

        // 如果只有一个值，那就直接获取；如果有多个节点，需要判断谁最小。
        if (children.size()==1){
            return;
        }
        else {
            Collections.sort(children);

            // 获取到对应的节点名称 seq-0000
            String thisNode = currentMode.substring("/locks/".length());

            // 通过seq-0000在集合中的位置
            int index = children.indexOf(thisNode);

            if (index ==-1 ){
                System.out.println("数据异常");
            } else if (index == 0) {
                // 只有一个节点，获取到锁
                return;
            }
            else {

                // 需要监听前一个节点变化
                waitPath="/locks/"+children.get(index-1);
                zk.getData(waitPath,true,null);

                // 等待监听
                waitLatch.await();

            }

        }

    }

    // 解锁
    public void zkunlock() throws Exception {

        // 删除节点
        zk.delete(currentMode,-1);
    }
}
