package com.example.devandrin.myapplication;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventItem {
    private int id,user_id;
    private String Name;
    private String Info;
    private long Date,CreatedTimestamp;
    private short status;
    private Invitation invite;
    public EventItem(String name, String info, long date) {
        Name = name;
        Info = info;
        Date = date;
    }

    public EventItem(int id, int user_id, String name, String info, long date, long createdTimestamp, short status) {
        this.id = id;
        this.user_id = user_id;
        Name = name;
        Info = info;
        Date = date;
        CreatedTimestamp = createdTimestamp;
        this.status = status;
    }
    private EventItem(JSONObject o) throws JSONException
    {
        id = o.getInt("id");
        user_id = o.getInt("user_id");
        Name = o.getString("title");
        Info = o.getString("description");
        Date = o.getLong("date");
        CreatedTimestamp = o.getLong("createdTimestamp");
        status = (short) o.getInt("status");
        if(o.has("invite_id"))
        {
            invite = new Invitation(o.getInt("invite_id"),o.getString("message"),o.getLong("sender_user_id"));
        }
    }
    public static ArrayList<EventItem> fromJSONArray(JSONArray arr)
    {
        ArrayList<EventItem> items = new ArrayList<>();
        try
        {
            for(int i =0; i<arr.length();i++)
            {
                items.add(new EventItem(arr.getJSONObject(i)));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return items;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getCreatedTimestamp() {
        return CreatedTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        CreatedTimestamp = createdTimestamp;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }
    public boolean hasInvite()
    {
        return invite != null;
    }

    public Invitation getInvite() {
        return invite;
    }

    public void setInvite(Invitation invite) {
        this.invite = invite;
    }
}
