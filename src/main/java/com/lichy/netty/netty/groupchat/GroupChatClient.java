package com.lichy.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 聊天室Client
 *
 * @author lichy
 */
public class GroupChatClient {

    private int port;
    private String host;

    public GroupChatClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap serverBootstrap = new Bootstrap();
            serverBootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.connect(host, port).sync();
            Channel channel = sync.channel();
            System.out.println("客户端启动完成");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                channel.writeAndFlush(scanner.nextLine()+"\n");
            }
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        GroupChatClient groupChatServer = new GroupChatClient(8888, "127.0.0.1");
        groupChatServer.run();
    }
}
