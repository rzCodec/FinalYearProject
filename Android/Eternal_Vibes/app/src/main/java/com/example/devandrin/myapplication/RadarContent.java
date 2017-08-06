package com.example.devandrin.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronnie on 2017-04-13.
 */

public class RadarContent {
    private int userID;
    private String sUsername;
    private String sLastName;
    private int Distance;
    private int Rating;
    private String Ranking;
    private String sLocation;
    private long TimeStamp;
    private String sEmail;
    private String Skillset;

    public RadarContent(){

    }

    public RadarContent(int userID, String sUsername, String sLastName, int distance, int rating, String ranking, String sLocation, long timeStamp, String sEmail, String Skillset) {
        this.userID = userID;
        this.sUsername = sUsername;
        this.sLastName = sLastName;
        Distance = distance;
        Rating = rating;
        Ranking = ranking;
        this.sLocation = sLocation;
        TimeStamp = timeStamp;
        this.sEmail = sEmail;
        this.Skillset = Skillset;
    }

    public RadarContent(JSONObject jsonObject){
        try{
            this.userID = jsonObject.getInt("id");
            this.sUsername = jsonObject.getString("username");
            this.Distance = jsonObject.getInt("distance");
            this.Rating = jsonObject.getInt("rating");
            this.Ranking = jsonObject.getString("ranking");
        }
        catch(JSONException jException){

        }

    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsLastName() {
        return sLastName;
    }

    public void setsLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getSkillset() {
        return Skillset;
    }

    public void setSkillset(String skillset) {
        Skillset = skillset;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        TimeStamp = timeStamp;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
