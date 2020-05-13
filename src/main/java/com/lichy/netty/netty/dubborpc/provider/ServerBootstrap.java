package com.lichy.netty.netty.dubborpc.provider;

import com.lichy.netty.netty.dubborpc.netty.NettyServer;

/**
 * 服务启动类
 * @author lichy
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer(8889);
    }
}
