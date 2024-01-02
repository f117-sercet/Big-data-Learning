package com.zookeeper.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/2 9:12
 */
public class zkClient
{

   private String connectString="hadoop102:2181,hadoop103:2181,hadoop104:2181";
   private int  sessionTimeout = 2000;
   private ZooKeeper zkClient;
    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {/*
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println(child);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }*/

            }
        });
    }

    @Test
    public void create() throws Exception {
        String nodeCreated = zkClient.create("/test", "test1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    @Test
    public void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws Exception {
        Stat exists = zkClient.exists("/test", false);
        System.out.println(exists==null?"不存在":exists);
    }
}
