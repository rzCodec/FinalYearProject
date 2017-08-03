package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/07/12.
 */

public class Message {
    int ChatID, SenderID, MessageID;
    byte isRead;
    long timestamp;
    String Message;

    public Message(int chatID, int senderID, int messageID, byte isRead, long timestamp, String message) {
        ChatID = chatID;
        SenderID = senderID;
        MessageID = messageID;
        this.isRead = isRead;
        this.timestamp = timestamp;
        Message = message;
    }

    @Override
    public String toString() {
        return "Message ID: " + MessageID +
                "\nChat ID: " + ChatID +
                "\nSender ID: " + SenderID +
                "\nIs Read: " + isRead +
                "\nTimestamp: " + timestamp +
                "\nMessage: " + Message;
    }
}
