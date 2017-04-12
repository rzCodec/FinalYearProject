package com.example.devandrin.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/11.
 */

public class NewsFeedItem {
    private int statusID;
    private String userID;
    private long timestamp;
    private String status;
    private String extraInfo;
    private int likes;
    private int flags;

    public NewsFeedItem(int statusID, String userID, long timestamp, String status, String extraInfo, int likes, int flags) {
        this.statusID = statusID;
        this.userID = userID;
        this.timestamp = timestamp;
        this.status = status;
        this.extraInfo = extraInfo;
        this.likes = likes;
        this.flags = flags;
    }

    public NewsFeedItem(JSONObject obj) {
        try {
            this.statusID = obj.getInt("statusID");
            this.userID = obj.getString("userID");
            this.timestamp = obj.getLong("timestamp");
            this.status = obj.getString("status");
            this.extraInfo = obj.getString("extraInfo");
            this.likes = obj.getInt("liked");
            this.flags = obj.getInt("flagged");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NewsFeedItem> ExtractData(JSONArray objs) {
        ArrayList<NewsFeedItem> items = new ArrayList<>();
        for (int i = 0; i < objs.length(); i++) {
            try {
                items.add(new NewsFeedItem(objs.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public int getStatusID() {
        return statusID;
    }

    public String getUserID() {
        return userID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public int getLikes() {
        return likes;
    }

    public int getFlags() {
        return flags;
    }
}
