package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Ronald on 7/26/2017.
 */

public class RadarThread implements Runnable {

    private ArrayList<RadarContent> unsorted_radarList;
    private RadarContent[] arrAPI_Profiles;
    private int distanceInMeters = 0;

    public RadarThread(){

    }

    @Override
    public void run(){

        //Thread runs in the background without clashing with the worker thread
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        String url = "https://www.eternalvibes.me/getListRadarUsers";
        int numProfiles = 5;

        /* Request method to be used soon
        JsonObjectRequest JOR = new JsonObjectRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        */

        setupProfiles(numProfiles);
    }

    /**
     * Sends a request and receives an array of profiles from the API response
     * Then calculates the distance of each user to check if they are within range
     */
    private void setupProfiles(int numProfiles){

        //Dummy profiles are used for now until the API to return actual profiles is implemented
        numProfiles = 5;
        arrAPI_Profiles = new RadarContent[numProfiles];

        arrAPI_Profiles[0] = new RadarContent(452, "Jessica Grom", 12, 3, "Intermediate", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob Brown", 20, 2, "Beginner", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom Yeis", 18, 4, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany Vlein", 40, 5, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry Alko", 34, 1, "Beginner", "Soweto", 800);

        //Calculate distance will be added soon
        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one
        unsorted_radarList = new ArrayList<>();

        for(int i = 0; i < arrAPI_Profiles.length; i++){
            if(arrAPI_Profiles[i].getDistance() <= 45) {
                unsorted_radarList.add(arrAPI_Profiles[i]);
            }
        }
    }

    /**
     * This calculates the distance in meters between two user's longitude and latitude coordinates
     * @return a value in meters
     */
    private int calcDistanceInMeters(){
        return 0;
    }

    public ArrayList<RadarContent> getUnsorted_radarList() {
        return unsorted_radarList;
    }
}
