package com.example.smartchatapp;

public class Message {
    public String senderId;
    public String receiverId;
    public String message;
    public long timestamp;

    public Message() {
        // Required for Firebase
    }

    public Message(String senderId, String receiverId, String message, long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }
}
