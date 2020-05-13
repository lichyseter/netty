package com.lichy.netty.netty.protocoltcp;

/**
 * 协议包
 */
public class MessageProtocol {

    int length;

    byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
