package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
    private String resStringFromAPI = "";

    public RadarThread(){

    }

    public RadarThread(String userID){
        this.userID = userID;
    }

    @Override
    public void run(){

        //Thread runs in the background without clashing with the worker thread
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        //resStringFromAPI = "https://www.eternalvibes.me/getNearbyStrangers/" + userID;
        //String url = "https://www.eternalvibes.me/getNearbyStrangers/303346733";
        String url = "https://www.eternalvibes.me/getNearbyStrangers" + userID;

        numProfiles = 5;

        /*

        JsonArrayRequest JOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {

                temp = responseArray.length();

                for(int i = 0; i < responseArray.length(); i++){
                    try{
                        JSONObject o = responseArray.getJSONObject(i);
                        resStringFromAPI = o.toString();
                        Log.i("VOLLEY", "Response # " + i + " is >> " + o.toString());
                    }
                    catch(JSONException e){
                        Log.e("VOLLEY", "Error Response # " + i + "is >>>>>>>>>>>>>>>>>> " + e.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/


        /*
        JsonObjectRequest JOR = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray outerArray = response.getJSONArray("user");
                    JSONObject obj = response.getJSONObject("user");
                    temp = outerArray.length() + obj.length();
                }
                catch(JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){

        };*/

        //String url = "https://www.eternalvibes.me/sendmessage" + "/" + chatID + "/" + currentUserID;

        /*
        Map<String, Integer> data = new HashMap<>();
        data.put("userID", 303346733);

        final JSONObject jo = new JSONObject(data);

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", "Response for Radar is >>>>>>>>>>>>>>>>>> " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "Error from send message request is >>>>>>>>>>>>>> " + error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jo.toString() == null ? null : jo.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jo.toString(), "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = response.statusCode+"";
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        */

        RequestQueueSingleton.getInstance(HomeActivity.getInstance().getApplicationContext()).addToQ(sr);

        //RequestQueueSingleton.getInstance(HomeActivity.getInstance().getApplicationContext()).addToQ(JOR);
        setupProfiles(numProfiles, jRadarResponse);
    }

    /**
     * Sends a request and receives an array of profiles from the API response
     * Then calculates the distance of each user to check if they are within range
     */
    private void setupProfiles(int numProfiles, JSONObject jObject){

        //Dummy profiles are used for now until the API to return actual profiles is implemented
        numProfiles = 3;
        arrAPI_Profiles = new RadarContent[numProfiles];

        /*
        arrAPI_Profiles[0] = new RadarContent(452, "Jessica Grom : " + temp, 12, 3, "Intermediate", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob Brown", 20, 2, "Beginner", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom Yeis", 18, 4, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany Vlein", 40, 5, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry Alko", 34, 1, "Beginner", "Soweto", 800);*/

        arrAPI_Profiles[0] = new RadarContent(303346736, "John ", "Crester", 12, 3, "Intermediate", "Sandton", 2100, "Jess55@gmail.com", "Vocals");
        arrAPI_Profiles[1] = new RadarContent(303346737, "mrBob", "Breck", 20, 2, "Beginner", "Houghton", 500, "bob2@gmail.com", "Drums");
        arrAPI_Profiles[2] = new RadarContent(303346740, "Vanneh", "Kunni", 18, 4, "Advanced", "Randburg", 1500, "201320596@student.uj.ac.za", "Keyboard");
        //arrAPI_Profiles[3] = new RadarContent(452, "Tiffany", "Vlein", 40, 5, "Master", "Pretoria", 4100, "22Tiff@gmail.com", "Guitar");
        //arrAPI_Profiles[4] = new RadarContent(842, "Jerry's length is : " + temp, " Alko", 34, 1, "Beginner", "Soweto", 800, "Jerry@hotmail.com", "Piano");

        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one
        unsorted_radarList = new ArrayList<>();
        for(int i = 0; i < arrAPI_Profiles.length; i++){
                unsorted_radarList.add(arrAPI_Profiles[i]);
        }
    }

    public ArrayList<RadarContent> getUnsorted_radarList() {
        return unsorted_radarList;
    }

    public ArrayList<RadarContent> getDetailedRadarProfileList() {
        return detailedRadarProfileList;
    }

}//end of class