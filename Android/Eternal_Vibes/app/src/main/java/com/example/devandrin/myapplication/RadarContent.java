package com.example.devandrin.myapplication;

/**
 * Created by Ronnie on 2017-04-13.
 */

public class RadarContent
{
    private String sUsername;
    private String sDistance;
    private String sLocation;
    private long TimeStamp;

    public RadarContent(String sUsername, String sDistance, String sLocation, long timeStamp)
    {
        this.sUsername = sUsername;
        this.sDistance = sDistance;
        this.sLocation = sLocation;
        TimeStamp = timeStamp;
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsDistance() {
        return sDistance;
    }

    public void setsDistance(String sDistance) {
        this.sDistance = sDistance;
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
}
