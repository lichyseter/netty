package com.lichy.netty.netty.protobuf2;

import com.lichy.netty.netty.protobuf.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * nettyServer
 *
 * @author lichy
 */
public class NettyServer {

    public static void main(String[] args) {
        // loopGroup都是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 服务器端配置启动参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)  // 设置两个线程组
                    .channel(NioServerSocketChannel.class). // 使用SocketChannel作为服务器通道实现
                    option(ChannelOption.SO_BACKLOG, 128). // 设置线程队列得到的连接个数
                    childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufDecoder(DataPOJO.MyMessage.getDefaultInstance())).addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup的EventLoop设置管道处理器
            System.out.println("服务器配置完成");
            ChannelFuture sync = serverBootstrap.bind(6668).sync();
            sync.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("监听端口成功");
                } else {
                    System.out.println("监听端口失败");
                }
            });
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
