package com.lichy.netty.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 服务端处理消息的handler
 *
 * @author lichy
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MessageDecoder()).addLast(new MyServerHandler());
    }
}
