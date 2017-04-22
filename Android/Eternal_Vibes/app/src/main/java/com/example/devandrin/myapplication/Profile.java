package com.example.devandrin.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/19.
 */

public class Profile {
    int statusID;
    int userID;
    long timestamp;
    String status;
    String extraInfo;
    int flagged;
    int liked;
    public static Profile fromJsonObject(JSONObject o) throws JSONException
    {
        Profile value = new Profile();
        value.setStatusID(o.getInt("statusID"));
        value.setUserID(o.getInt("userID"));
        value.setTimestamp(o.getInt("timestamp"));
        value.setStatus(o.getString("status"));
        value.setExtraInfo(o.getString("extraInfo"));
        value.setLiked(o.getInt("liked"));
        value.setFlagged(o.getInt("flagged"));
        return value;
    }
    public static ArrayList<Profile> fromJsonArray(JSONArray array) throws JSONException
    {
        ArrayList<Profile> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++)
        {
            list.add(Profile.fromJsonObject(array.getJSONObject(i)));
        }
        return list;
    }
    public Profile(){}
    public Profile(int statusID, int userID, long timestamp, String status, String extraInfo, int flagged, int liked) {
        this.statusID = statusID;
        this.userID = userID;
        this.timestamp = timestamp;
        this.status = status;
        this.extraInfo = extraInfo;
        this.flagged = flagged;
        this.liked = liked;
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

    public int getFlagged() {
        return flagged;
    }

    public void setFlagged(int flagged) {
        this.flagged = flagged;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Details :\n"
                + statusID + "\n"
                + userID + "\n"
                + timestamp + "\n"
                + status + "\n"
                + extraInfo + "\n"
                + flagged + "\n"
                + liked + "\n";
    }
}
