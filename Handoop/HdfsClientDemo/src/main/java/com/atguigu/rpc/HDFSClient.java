package com.atguigu.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Description： hdfs客户端
 *
 * @author: 段世超
 * @aate: Created in 2022/3/29 17:08
 */
public class HDFSClient {

    public static void main(String[] args) throws IOException {

        // 获取客户端对象
        RPCProtocol client = RPC.getProxy(RPCProtocol.class, RPCProtocol.versionID, new InetSocketAddress("localhost", 8888),
                new Configuration());

        System.out.println("客户端开始工作");

        client.mkdirs("/input");
    }
}
