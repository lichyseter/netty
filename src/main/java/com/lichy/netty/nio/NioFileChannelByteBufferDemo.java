package com.lichy.netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试FileChannelByteBuffer的样例
 * @author lichy
 */
public class NioFileChannelByteBufferDemo {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("测试source.txt"));
            fileOutputStream = new FileOutputStream(new File("测试target.txt"));
            FileChannel inputStreamChannel =fileInputStream.getChannel();
            FileChannel outputStreamChannel =fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);
            while(true) {
                byteBuffer.clear();
                long length = inputStreamChannel.read(byteBuffer);
                if (length <= 0) {
                    break;
                }
                byteBuffer.flip();
                outputStreamChannel.write(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
