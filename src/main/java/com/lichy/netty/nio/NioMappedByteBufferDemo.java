package com.lichy.netty.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以在内存(堆外内存)将文件直接修改，不需要操作系统拷贝一次
 *
 * @author lichy
 */
public class NioMappedByteBufferDemo {

    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(new File("测试source.txt"), "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 9, 4);
            // 其中put这个index是map函数第三个size参数里的顺序，而不是整个串的顺序。
            mappedByteBuffer.put(0, (byte) '6');
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
