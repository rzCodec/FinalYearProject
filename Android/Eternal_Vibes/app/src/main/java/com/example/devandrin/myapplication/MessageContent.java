package com.example.devandrin.myapplication;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class MessageContent
{
    private String sMessage;
    private String sYourMessage;
    private String sReplyMessage;
    private Boolean isMessageMine;

    public MessageContent(Boolean isMessageMine, String sMessage)
    {
        this.isMessageMine = isMessageMine;
        this.sMessage = sMessage;
    }

    public MessageContent(String sYourMessage, String sReplyMessage)
    {
        this.sYourMessage = sYourMessage;
        this.sReplyMessage = sReplyMessage;
    }

    //Getters and Setters


    public Boolean getMessageMine() {
        return isMessageMine;
    }

    public void setMessageMine(Boolean messageMine) {
        isMessageMine = messageMine;
    }

    public String getsMessage()
    {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public String getsYourMessage()
    {
        return sYourMessage;
    }

    public void setsYourMessage(String sYourMessage)
    {
        this.sYourMessage = sYourMessage;
    }

    public String getsReplyMessage()
    {
        return sReplyMessage;
    }

    public void setsReplyMessage(String sReplyMessage)
    {
        this.sReplyMessage = sReplyMessage;
    }
}
