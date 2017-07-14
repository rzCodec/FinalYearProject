package com.example.devandrin.myapplication;

/**
 * Created by Ronnie on 2017-04-13.
 */

public class RadarContent {
    private int userID;
    private String sUsername;
    private int Distance;
    private int Rating;
    private String Ranking;
    private String sLocation;
    private long TimeStamp;

    public RadarContent(int userID, String sUsername, int distance, int rating, String ranking, String sLocation, long timeStamp) {
        this.userID = userID;
        this.sUsername = sUsername;
        Distance = distance;
        Rating = rating;
        Ranking = ranking;
        this.sLocation = sLocation;
        TimeStamp = timeStamp;
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
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
