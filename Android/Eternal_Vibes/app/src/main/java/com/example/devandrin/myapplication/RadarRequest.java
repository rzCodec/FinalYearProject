package com.example.devandrin.myapplication;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ronald on 8/10/2017.
 */

public class RadarRequest {
    ArrayList<RadarContent> unsortedRadarResponseList = new ArrayList<>();

    public RadarRequest(){

    }

    public RadarRequest(String activeuserID){
        extractNearbyStrangersData(activeuserID);
    }

    /**
     * Make a request to find a list of muscians within the user's area
     * @param activeuserID - The current user id of the app
     * @return a list of user ids and distances
     */
    public ArrayList<RadarContent> extractNearbyStrangersData(String activeuserID){
        String sReq = "https://www.eternalvibes.me/getNearbyStrangers/" + activeuserID;
        JsonArrayRequest JOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sReq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                for(int i = 0; i < responseArray.length(); i++){
                    try{
                         /*The response array is in the following JSON format
                         *
                         * [{"user"{"id", "longitude", "latitude"} "km"}, etc]
                         */
                        JSONObject jOuterObj = responseArray.getJSONObject(i);
                        JSONObject innerObj = jOuterObj.getJSONObject("user");
                        String userID = innerObj.getString("id");
                        double longitude = innerObj.getDouble("longitude");
                        double latitude = innerObj.getDouble("latitude");
                        int distance = (int) jOuterObj.getDouble("km");
                        unsortedRadarResponseList = extractUserInfo(userID, distance);
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                Toast.makeText(HomeActivity.getInstance(), "List size is -> " + unsortedRadarResponseList.size(),
                        Toast.LENGTH_LONG).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(JOR);
        return unsortedRadarResponseList;
    }

    /**
     * Make a request to the API to get a profile's information
     * @param sUserID - The user id obtained from the radar
     * @param iDistance - Distance between current user and found user
     * @return
     */
    private ArrayList<RadarContent> extractUserInfo(String sUserID, final int iDistance){
        String sGetProfileReq = "https://www.eternalvibes.me/getuserinfo/" + sUserID;

        JsonArrayRequest arrJOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sGetProfileReq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                RadarContent rcObj = new RadarContent(responseArray);
                rcObj.setDistance(iDistance);
                rcObj.setRanking("<Ranking will go here>");
                rcObj.setRating(new Random().nextInt(5) + 1);
                rcObj.setsEmail("temp@gmail.com");
                rcObj.setSkillset("<Skills will go here>");
                rcObj.setsLocation("<Location will go here>");
                unsortedRadarResponseList.add(rcObj);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(arrJOR);
        return unsortedRadarResponseList;
    }
}//end of class
