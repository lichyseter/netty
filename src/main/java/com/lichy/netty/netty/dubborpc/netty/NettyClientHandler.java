package com.lichy.netty.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext channelHandlerContext;

    private String result;

    private String param;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg == null ? null : msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 发送数据给服务器，并等待返回
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        channelHandlerContext.writeAndFlush(param);
        wait();
        return result;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
