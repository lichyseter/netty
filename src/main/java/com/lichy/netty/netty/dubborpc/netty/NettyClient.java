package com.lichy.netty.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler nettyClientHandler;

    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, (proxy, method, args) -> {
            if (nettyClientHandler == null) {
                initClient();
            }
            nettyClientHandler.setParam(providerName + args[0]);
            return executorService.submit(nettyClientHandler).get();
        });
    }

    private static void initClient() {
        nettyClientHandler = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap serverBootstrap = new Bootstrap();
            serverBootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(nettyClientHandler);
                        }
                    });
            serverBootstrap.connect("127.0.0.1", 8889).sync();
            System.out.println("客户端启动完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
