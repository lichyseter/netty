package com.lichy.netty.netty.protobuf2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 客户端端处理消息的handler
 *
 * @author lichy
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = ThreadLocalRandom.current().nextInt(2);
        if (i == 0) {
            ctx.writeAndFlush(DataPOJO.MyMessage.newBuilder().setDataType(DataPOJO.MyMessage.DataType.StudentType)
                    .setStudent(DataPOJO.Student.newBuilder().setId(1).setName("lichy")).build());
        } else {
            ctx.writeAndFlush(DataPOJO.MyMessage.newBuilder().setDataType(DataPOJO.MyMessage.DataType.WorkerType)
                    .setWorker(DataPOJO.Worker.newBuilder().setAge("10").setName(11).build()).build());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("client接受信息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("消息来源：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
