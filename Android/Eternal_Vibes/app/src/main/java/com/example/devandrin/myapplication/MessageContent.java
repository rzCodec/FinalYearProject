package com.example.devandrin.myapplication;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class MessageContent {
    private String sYourMessage;
    private String sReplyMessage;

    public MessageContent(String sYourMessage, String sReplyMessage) {
        this.sYourMessage = sYourMessage;
        this.sReplyMessage = sReplyMessage;
    }

    //Getters and Setters

    public String getsYourMessage() {
        return sYourMessage;
    }

    public void setsYourMessage(String sYourMessage) {
        this.sYourMessage = sYourMessage;
    }

    public String getsReplyMessage() {
        return sReplyMessage;
    }

    public void setsReplyMessage(String sReplyMessage) {
        this.sReplyMessage = sReplyMessage;
    }
}
