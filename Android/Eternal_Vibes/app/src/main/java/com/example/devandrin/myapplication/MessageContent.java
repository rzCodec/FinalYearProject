package com.example.devandrin.myapplication;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class MessageContent
{
    private long userID;
    private long recipientID;
    private String sMessage;
    private Boolean isMessageMine;

    public MessageContent(Boolean isMessageMine, String sMessage)
    {
        this.isMessageMine = isMessageMine;
        this.sMessage = sMessage;
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


}
