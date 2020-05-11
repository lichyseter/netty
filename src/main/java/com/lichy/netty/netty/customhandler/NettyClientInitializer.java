package com.lichy.netty.netty.customhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端端处理消息的handler
 *
 * @author lichy
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LongToByteEncoder()).addLast(new MyOutboundHandler());
    }
}
