package com.example.devandrin.myapplication;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ronnie on 2017-04-13.
 */

public class RadarContent {
    private int userID;
    private String sUsername;
    private String sAlias;
    private String sLastName;
    private int Distance;
    private int Rating;
    private String Ranking;
    private String sLocation;
    private long TimeStamp;
    private String sEmail;
    private String Skillset;

    private double longitude;
    private double latitude;
    private ArrayList<RadarContent> unsortedRC_List = new ArrayList<>();

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

    /**
     * Overloaded constructor is used in the RadarRequest class
     * @param arr - An array object received from the node.js API created by David
     */
    public RadarContent(JSONArray arr){
        try{
            this.sUsername = arr.getJSONObject(0).getString("firstname");
            this.sAlias = arr.getJSONObject(0).getString("username");
            this.sLastName = arr.getJSONObject(0).getString("surname");
        }
        catch(JSONException jException){

        }
    }

    /**
     * Overloaded constructor is used in the RadarRequest class
     * @param o - An object received from the node.js API created by David
     */
    public RadarContent(JSONObject o){
        try{
            this.sUsername = o.getString("firstname");
            this.sAlias = o.getString("username");
            this.sLastName = o.getString("surname");
            //To be completed properly
            this.Rating = new Random().nextInt(5) + 1;
            this.Ranking = "Temporary";
            this.Distance = new Random().nextInt(100) + 1;
        }
        catch(JSONException jException){

        }
    }

    /**
     * Another overloaded constructor used to instantiate an object for the getNearbyStrangers response
     * @param userID
     * @param longitude
     * @param latitude
     * @param distance
     */
    public RadarContent(int userID, double longitude, double latitude, int distance){
        this.userID = userID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.Distance = distance;
    }

    public static ArrayList<RadarContent> fromJSONResponseArray(JSONArray arr){
        ArrayList<RadarContent> rcList = new ArrayList<>();
        try {
            for(int i=0; i < arr.length();i++) {
                rcList.add(new RadarContent(arr.getJSONObject(i)));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return rcList;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getsAlias() {
        return sAlias;
    }

    public void setsAlias(String sAlias) {
        this.sAlias = sAlias;
    }

    public ArrayList<RadarContent> getUnsortedRC_List() {
        return unsortedRC_List;
    }

    public void setUnsortedRC_List(ArrayList<RadarContent> unsortedRC_List) {
        this.unsortedRC_List = unsortedRC_List;
    }

    public String getNearbyStrangersResponseString(){
        return "User ID is -> " + userID + "\n" +
                "Longitude is -> " + longitude + "\n" +
                "Latitude is -> " + latitude + "\n" +
                "Distance is -> " + Distance;
    }

    @Override
    public String toString() {
        return "RadarContent{" +
                "userID=" + userID +
                ", sUsername='" + sUsername + '\'' +
                ", sAlias='" + sAlias + '\'' +
                ", sLastName='" + sLastName + '\'' +
                ", Distance=" + Distance +
                ", Rating=" + Rating +
                ", Ranking='" + Ranking + '\'' +
                ", sLocation='" + sLocation + '\'' +
                ", TimeStamp=" + TimeStamp +
                ", sEmail='" + sEmail + '\'' +
                ", Skillset='" + Skillset + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
