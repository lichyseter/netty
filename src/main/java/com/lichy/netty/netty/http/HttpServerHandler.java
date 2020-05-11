package com.lichy.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 简单http服务器handler
 *
 * @author lichy
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());
            if (((HttpRequest) msg).uri().endsWith("/favicon.ico")) {
                System.out.println("请求favicon.ico");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("hello我是服务器", CharsetUtil.UTF_8);
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(defaultFullHttpResponse);
        }
    }
}
