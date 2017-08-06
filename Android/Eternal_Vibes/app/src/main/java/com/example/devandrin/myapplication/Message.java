package com.example.devandrin.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private Message(JSONObject o) throws JSONException
    {
        ChatID = o.getInt("chat_id");
        SenderID = o.getInt("sender_id");
        MessageID = o.getInt("id");
        isRead = Byte.valueOf(o.getInt("is_read")+"");
        timestamp = o.getLong("timestamp");
        Message = o.getString("message");
    }
    public static ArrayList<Message> fromJSONArray(JSONArray arr)
    {
        ArrayList<Message> m = new ArrayList<>();
        try
        {
            for(int i=0; i<arr.length();i++)
            {
                m.add(new Message(arr.getJSONObject(i)));
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
        return m;
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
