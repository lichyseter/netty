package com.lichy.netty.nio;

import java.nio.IntBuffer;
import java.util.stream.Stream;

/**
 * 基础buffer样例类
 * @author lichy
 */
public class BasicBuffer {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        Stream.of(1, 2, 3, 4, 5).forEach(i -> intBuffer.put(i * 2));
        // 读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
