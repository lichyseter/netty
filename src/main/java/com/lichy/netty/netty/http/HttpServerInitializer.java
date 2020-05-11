package com.lichy.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 简单http服务器Initializer
 *
 * @author lichy
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 得到管道,添加编解码器，添加自定义Handler
        ch.pipeline().addLast("myHttpServerCodec", new HttpServerCodec()).addLast("myHandler", new HttpServerHandler());
    }
}
