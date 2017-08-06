package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Ronald on 7/26/2017.
 */

public class RadarThread implements Runnable {

    private class RadarProfile{
        private String userID = "";
        private double longitude = 0;
        private double latitude = 0;

        public RadarProfile(JSONObject o){
            try{
                this.userID = o.getString("userID");
            }
            catch(JSONException e){

            }
        }
    }

    private ArrayList<RadarContent> unsorted_radarList;
    private RadarContent[] arrAPI_Profiles;
    private ArrayList<RadarContent> detailedRadarProfileList;
    private String userID = "Hello Default String";
    private JSONObject jRadarResponse = null;
    private int numProfiles, temp;

    public RadarThread(){

    }

    public RadarThread(String userID){
        this.userID = userID;
    }

    @Override
    public void run(){

        //Thread runs in the background without clashing with the worker thread
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        String url = "https://www.eternalvibes.me/getNearbyStrangers/" + userID;
        numProfiles = 5;

        JsonArrayRequest JOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {

                numProfiles = responseArray.length();
                temp = responseArray.length() + 1;
                RadarProfile[] arrRadarProfiles = new RadarProfile[numProfiles];

                for(int i = 0; i < responseArray.length(); i++){
                    /*
                    try {
                        arrRadarProfiles[i] = new RadarProfile(responseArray.getJSONObject(i));
                    }
                    catch(JSONException e){

                    }
                    */
                }

                /*
                for(int i = 0; i < responseArray.length(); i++){
                    try
                    {
                        arrAPI_Profiles[i] = new RadarContent(responseArray.getJSONObject(i));
                    }
                    catch (JSONException e) {
                        Toast.makeText(HomeActivity.getInstance(), "Could not get the JSON data.",
                                Toast.LENGTH_LONG).show();
                    }

                }
                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        setupProfiles(numProfiles, jRadarResponse);
    }

    /**
     * Sends a request and receives an array of profiles from the API response
     * Then calculates the distance of each user to check if they are within range
     */
    private void setupProfiles(int numProfiles, JSONObject jObject){

        //Dummy profiles are used for now until the API to return actual profiles is implemented
        numProfiles = 5;
        arrAPI_Profiles = new RadarContent[numProfiles];

        /*
        arrAPI_Profiles[0] = new RadarContent(452, "Jessica Grom : " + temp, 12, 3, "Intermediate", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob Brown", 20, 2, "Beginner", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom Yeis", 18, 4, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany Vlein", 40, 5, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry Alko", 34, 1, "Beginner", "Soweto", 800);*/

        arrAPI_Profiles[0] = new RadarContent(452, "Jessica", "Grom", 12, 3, "Intermediate", "Sandton", 2100, "Jess55@gmail.com");
        arrAPI_Profiles[1] = new RadarContent(89, "Bob", "Brown", 20, 2, "Beginner", "Houghton", 500, "Bob85@gmail.com");
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom", "Yeis", 18, 4, "Advanced", "Randburg", 1500, "Tom@44hotmail.com");
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany", "Vlein", 40, 5, "Master", "Pretoria", 4100, "22Tiff@gmail.com");
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry", " Alko", 34, 1, "Beginner", "Soweto", 800, "Jerry@hotmail.com");


        /*
        for(int i = 0; i < numProfiles; i++){
           arrAPI_Profiles[i] = new RadarContent();

        }
         */

        //Calculate distance will be added soon
        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one
        unsorted_radarList = new ArrayList<>();

        for(int i = 0; i < arrAPI_Profiles.length; i++){
            if(arrAPI_Profiles[i].getDistance() <= 45) {
                unsorted_radarList.add(arrAPI_Profiles[i]);
            }
        }
    }

    public ArrayList<RadarContent> getUnsorted_radarList() {
        return unsorted_radarList;
    }

    public ArrayList<RadarContent> getDetailedRadarProfileList() {
        return detailedRadarProfileList;
    }

}//end of class
