package com.example.devandrin.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Devandrin on 2017/04/11.
 */

public class NewsFeedItem {
    private int statusID;
    private int userID;
    private long timestamp;
    private String status;
    private String extraInfo;
    private int likes;
    private int flags;

    public NewsFeedItem(int statusID, int userID, long timestamp, String status, String extraInfo, int likes, int flags) {
        this.statusID = statusID;
        this.userID = userID;
        this.timestamp = timestamp;
        this.status = status;
        this.extraInfo = extraInfo;
        this.likes = likes;
        this.flags = flags;
    }

    private NewsFeedItem(JSONObject obj) {
        try {
            this.statusID = obj.getInt("id");
            this.userID = obj.getInt("user_id");
            this.timestamp = obj.getLong("timestamp");
            this.status = obj.getString("status");
            this.extraInfo = obj.getString("extra_info");
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
        Collections.reverse(items);
        return items;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}
