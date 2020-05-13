package com.lichy.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * 客户端端处理消息的handler
 *
 * @author lichy
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String msg = "hello" + i;
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLength(content.length);
            ctx.channel().writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
//        byte[] buffer = new byte[msg.readableBytes()];
//        msg.readBytes(buffer);
//        System.out.println(new String(buffer, StandardCharsets.UTF_8));
//        System.out.println("客户端接收消息量" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
