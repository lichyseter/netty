package com.lichy.netty.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NIO样例客户端
 * @author lichy
 */
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
            System.out.println("客户端配置完成");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
        }


    }
}
