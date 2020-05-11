package com.lichy.netty.netty.protobuf2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 服务端处理消息的handler
 *
 * @author lichy
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DataPOJO.MyMessage data = (DataPOJO.MyMessage) msg;
        if (data.getDataType() == DataPOJO.MyMessage.DataType.StudentType) {
            DataPOJO.Student student = data.getStudent();
            System.out.println("id:" + student.getId() + ",name:" + student.getName());
        } else if (data.getDataType() == DataPOJO.MyMessage.DataType.WorkerType) {
            DataPOJO.Worker worker = data.getWorker();
            System.out.println("age:" + worker.getAge() + ",name:" + worker.getName());
        } else {
            System.out.println("非法的类型");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello客户端", CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
