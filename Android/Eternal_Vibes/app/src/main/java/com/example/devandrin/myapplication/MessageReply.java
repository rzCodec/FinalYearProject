package com.example.devandrin.myapplication;
import java.util.*;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class MessageReply
{
    private ArrayList<String> msgList;
    private int iSelector;
    private int iChosen;

    public MessageReply()
    {
        msgList = new ArrayList<String>();
        addMessages("");
    }

    //Helper function
    private void addMessages(String sCustomMessage)
    {
        msgList.add("Hello there!");
        msgList.add("The studio is locked.");
        msgList.add("Stuck in heavy traffic, I will be a bit late for the meetup.");
        msgList.add("Working on a new song today.");
        msgList.add("This beat sounds good!");
        msgList.add("This is a new song snippet.");
    }

    public String generateReplyMsg()
    {
        Random rand = new Random();
        iSelector = rand.nextInt(6) + 0;
        if(iSelector != iChosen)
        {
            setChosen(iSelector);
            return msgList.get(iSelector);
        }
        else
        {
            iSelector = rand.nextInt(6) + 0;
        }

        return "A lot of artists like this song.";
    }

    //Getters
    public void setSelector(int iSelector)
    {
        this.iSelector = iSelector;
    }

    public void setChosen(int iChosen)
    {
        this.iChosen = iChosen;
    }
}
