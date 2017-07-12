package com.example.devandrin.myapplication;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/09.
 */

public class Chat {

    int ChatID, user1, user2;
    Chat(int chatID, int user1, int user2) {
        ChatID = chatID;
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "Chat ID: "+ChatID+
                "\nUser 1 ID: "+user1+
                "\nUser 2 ID: "+user2;
    }
    Chat(JSONObject o)
    {
        try
        {
            ChatID = o.getInt("id");
            user1=o.getInt("user1_id");
            user2=o.getInt("user2_id");
        }
        catch(JSONException e)
        {
            //do nothing
        }
    }
    public static ArrayList<Chat> extractAll(JSONArray o)
    {
        ArrayList<Chat> arr = new ArrayList<>();
        try
        {
            for(int i=0; i<o.length();i++)
            {
                arr.add(new Chat(o.getJSONObject(i)));
            }
        }
        catch(JSONException e)
        {
            //do nothing
        }
        return arr;
    }
}
