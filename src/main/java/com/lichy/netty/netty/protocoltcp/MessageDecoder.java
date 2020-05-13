package com.lichy.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<MessageProtocol> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 将得到的二进制转化为对象
        System.out.println("服务端解码");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
