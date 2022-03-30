package com.atguigu.rpc;

/**
 * Description： RPC接口
 *
 * @author: 段世超
 * @aate: Created in 2022/3/29 17:12
 */
public interface RPCProtocol {

    long versionID = 666;

    void mkdirs(String path);
}
