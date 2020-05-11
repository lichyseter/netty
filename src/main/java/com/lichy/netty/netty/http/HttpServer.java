package com.lichy.netty.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 简单http服务器
 *
 * @author lichy
 */
public class HttpServer {

    public static void main(String[] args) {
        // loopGroup都是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 服务器端配置启动参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)  // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用SocketChannel作为服务器通道实现
                    .childHandler(new HttpServerInitializer()); // 给我们的workerGroup的EventLoop设置管道处理器
            System.out.println("服务器配置完成");
            ChannelFuture sync = serverBootstrap.bind(8888).sync();
            // 对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
