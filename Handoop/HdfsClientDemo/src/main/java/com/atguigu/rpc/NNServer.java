package com.atguigu.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * Description： 实现通信接口
 *
 * @author: 段世超
 * @aate: Created in 2022/3/29 17:13
 */
public class NNServer implements RPCProtocol{

    public static void main(String[] args) throws IOException {
        //启动服务
        RPC.Server server = new RPC.Builder(new Configuration())
                .setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(RPCProtocol.class)
                .setInstance(new NNServer())
                .build();

        System.out.println("服务器开始工作");
        server.start();

    }

    @Override
    public void mkdirs(String path) {
        System.out.println("服务器接收到了客户端请求"+path);

    }
}
