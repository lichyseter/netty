package com.lichy.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("数据长度：" + msg.length + "," + "内容：" + new String(msg.content, StandardCharsets.UTF_8));
        System.out.println("服务器接收消息量" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
