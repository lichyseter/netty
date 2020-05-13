package com.lichy.netty.netty.dubborpc.provider;

import com.lichy.netty.netty.dubborpc.publicinterface.HelloService;

/**
 * 服务实现类
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息：" + msg);
        if (msg == null) {
            return "你好客户端";
        } else {
            return "你好客户端,收到消息：" + msg;
        }
    }
}
