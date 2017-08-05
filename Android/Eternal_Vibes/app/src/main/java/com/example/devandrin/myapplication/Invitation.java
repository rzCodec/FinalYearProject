package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/08/05.
 */

public class Invitation {
    private int id;
    private String Message;
    private int sender_id ;

    public Invitation(int id, String message, int sender_id) {
        this.id = id;
        Message = message;
        this.sender_id = sender_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }
}
