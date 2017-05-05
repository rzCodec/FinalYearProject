package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/04/09.
 */

public class Chat {
	private int iChatID;
    private long TimeStamp;
    private String Name;
    private String LastMessage;

    public Chat(long timeStamp, String name, String lastMessage) {
        TimeStamp = timeStamp;
        Name = name;
        LastMessage = lastMessage;
    }

    public long getTimestamp() {
        return TimeStamp;
    }

    public String getName() {
        return Name;
    }

    public String getLastMessage() {
        return LastMessage;
    }
}
