package com.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2024/1/2 15:02
 */
public class curatorLockTest {

    public static void main(String[] args) {
        // 创建分布式锁1
        InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");
        // 创建分布式锁2
        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("lock1 获取到锁");
                    lock1.acquire();
                    System.out.println("lock1 再次到锁");

                    Thread.sleep(5*1000);

                    lock1.release();
                    System.out.println("lock1 释放锁");

                    lock1.release();
                    System.out.println("lock1 再次释放锁");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("线程1获取到锁");
            }

        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("lock2 获取到锁");
                    lock1.acquire();
                    System.out.println("lock2 再次到锁");

                    Thread.sleep(5*1000);

                    lock1.release();
                    System.out.println("lock2 释放锁");

                    lock1.release();
                    System.out.println("lock2 再次释放锁");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("线程2获取到锁");
            }

        }).start();
    }


    private static CuratorFramework getCuratorFramework() {

        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(3000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("hadoop102:2181,hadoop103:2181,hadoop104:2181")
                .connectionTimeoutMs(2000)
                .retryPolicy(policy)
                .build();

        //启动客户端
        client.start();
        System.out.println("zookeeper 启动成功");
        return client;
    }
}
