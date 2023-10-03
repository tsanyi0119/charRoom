package com.example.chatroom;

public class Message {
    private String message;
    private String sender;
    private String receiver;
    private long timestamp;

    // 默认构造函数
    public Message() {
        // 默认构造函数通常是空的，因为在Firebase中，对象的属性会通过setter方法设置
    }

    // 构造函数
    public Message(String message, String sender,String receiver, long timestamp) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
