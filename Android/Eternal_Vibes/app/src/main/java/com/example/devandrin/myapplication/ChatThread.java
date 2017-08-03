package com.example.devandrin.myapplication;

/**
 * Created by Ronald on 8/3/2017.
 */

public class ChatThread extends Thread {

    private String req = "";
    private String sChatMessages = "";

    public ChatThread(){

    }

    public ChatThread(String req){
        this.req = req;
    }

    @Override
    public void run(){
        //Make chat request here
        sChatMessages = "Hello JSONResponse!";
    }

    public String returnChatMessages(){
        return sChatMessages;
    }
}
