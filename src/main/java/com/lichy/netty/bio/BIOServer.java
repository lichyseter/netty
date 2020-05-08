package com.lichy.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIOServer样例
 *
 * @author lichy
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("服务端接受一个连接" + socket.getInetAddress());
            executorService.execute(() -> {
                InputStream inputStream = null;
                try {
                    byte[] bytes = new byte[1024];
                    inputStream = socket.getInputStream();
                    while (true) {
                        int length = inputStream.read(bytes);
                        if (length >= 0) {
                            System.out.println(new String(bytes, 0, length));
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("服务端关闭一个连接" + socket.getInetAddress());
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
