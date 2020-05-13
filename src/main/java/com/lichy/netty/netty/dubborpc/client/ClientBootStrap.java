package com.lichy.netty.netty.dubborpc.client;

import com.lichy.netty.netty.dubborpc.netty.NettyClient;
import com.lichy.netty.netty.dubborpc.publicinterface.HelloService;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ClientBootStrap {

    public static final String PROVIDER_NAME = "HelloService#lichy#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, PROVIDER_NAME);
        for (int i = 0; i < 2; i++) {
            String msg = helloService.hello("gg"+i);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("调用结果为：" + msg);
        }

    }
}
