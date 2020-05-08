package com.lichy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 可以采用Buffer数组来进行写入和读
 *
 * @author lichy
 */
public class ScatteringGatheringDemo {

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
            serverSocketChannel.socket().bind(inetSocketAddress);
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);
            int totalLength = 8;
            while (true) {
                int writeLength = 0;
                while (writeLength < totalLength) {
                    long l = socketChannel.read(byteBuffers);
                    writeLength += l;
                    System.out.println("读取个数" + writeLength);
                    Arrays.stream(byteBuffers).forEach(byteBuffer -> System.out.println("position=" + byteBuffer.position() + " limit=" + byteBuffer.limit()));
                }
                Arrays.stream(byteBuffers).forEach(Buffer::flip);
                int readLength = 0;
                while (readLength < totalLength) {
                    readLength += socketChannel.write(byteBuffers);
                }
                Arrays.stream(byteBuffers).forEach(Buffer::clear);
                System.out.println("读取" + writeLength + "写" + readLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
