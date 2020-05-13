package com.lichy.netty.netty.dubborpc.netty;

import com.lichy.netty.netty.dubborpc.client.ClientBootStrap;
import com.lichy.netty.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端在调用服务器api需要定义一个协议
        // 每次发送消息都需要以某个标识开头
        System.out.println("接受数据中");
        if (msg != null) {
            String message = msg.toString();
            if (message.startsWith(ClientBootStrap.PROVIDER_NAME)) {
                ctx.writeAndFlush(new HelloServiceImpl().hello(message.substring(ClientBootStrap.PROVIDER_NAME.length(), msg.toString().length())));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
