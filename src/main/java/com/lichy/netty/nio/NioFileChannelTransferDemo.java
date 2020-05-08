package com.lichy.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 测试FileChannelTransfer的样例
 *
 * @author lichy
 */
public class NioFileChannelTransferDemo {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("测试source.txt"));
            fileOutputStream = new FileOutputStream(new File("测试target.txt"));
            FileChannel inputStreamChannel = fileInputStream.getChannel();
            FileChannel outputStreamChannel = fileOutputStream.getChannel();
            inputStreamChannel.transferTo(0, inputStreamChannel.size(), outputStreamChannel);
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
