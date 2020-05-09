package com.lichy.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 聊天室客户端
 *
 * @author lichy
 */
public class GroupChatClient {
    /**
     * host
     */
    private final String HOST = "127.1.0.1";
    /**
     * 端口
     */
    private final int PORT = 6667;
    /**
     * Selector
     */
    private Selector selector;
    /**
     * ServerSocket
     */
    private SocketChannel socketChannel;
    /**
     * 标识客户端名称
     */
    private String username;

    public GroupChatClient() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(PORT));
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + "客户端创建完成");
    }

    public void sendInfo(String info) {
        String msg = username + "说:" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取信息
     */
    public void readInfo() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if (selectionKey.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            channel.read(byteBuffer);
                            String msg = new String(byteBuffer.array());
                            System.out.println(msg);
                        }
                        selectionKeyIterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(() -> {
            while (true) {
                groupChatClient.readInfo();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            groupChatClient.sendInfo(scanner.nextLine());
        }
    }
}
