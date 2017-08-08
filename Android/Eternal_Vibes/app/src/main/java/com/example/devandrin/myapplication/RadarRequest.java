package com.example.devandrin.myapplication;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.nearby.messages.Distance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ronald on 8/8/2017.
 */

public class RadarRequest {
    private String sReq = "";
    private ArrayList<RadarContent> rcList;
    private RadarContent[] arrNearbyStrangersResponse;
    private RadarContent rcObj;
    private int iNumProfiles = 0;
    private int counter = 0;

    public RadarRequest(){

    }

    public RadarRequest(String sReq){
        this.sReq = sReq;
        rcList = new ArrayList<RadarContent>();
        rcObj = new RadarContent();
    }

    /**
     * This method works properly, it sends a request to retrieve all the users within the search radius
     * @return
     */
    public ArrayList<RadarContent> sendRequestAndReceiveResponse(){
        JsonArrayRequest JOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sReq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                arrNearbyStrangersResponse = new RadarContent[responseArray.length()];
                String userID = "";
                double longitude = 0;
                double latitude = 0;
                int distance = 0;

                for(int i = 0; i < responseArray.length(); i++){
                    try{
                        JSONObject jOuterObj = responseArray.getJSONObject(i);
                        JSONObject innerObj = jOuterObj.getJSONObject("user");
                        userID = innerObj.getString("id");
                        longitude = innerObj.getDouble("longitude");
                        latitude = innerObj.getDouble("latitude");
                        distance = (int) jOuterObj.getDouble("km");

                        ProfileRequest(userID);
                        arrNearbyStrangersResponse[i] = new RadarContent(Integer.parseInt(userID), longitude, latitude, distance);

                        Toast.makeText(HomeActivity.getInstance(), "Profile # " + i + "\n" + arrNearbyStrangersResponse[i].getNearbyStrangersResponseString(),
                                Toast.LENGTH_LONG).show();
                    }
                    catch(JSONException e){
                        Log.e("Volley", "Error from getNearbyStrangersAPI" + e.toString());
                    }
                }//end for loop

                //Testing Code Purposes Below
                //Toast.makeText(HomeActivity.getInstance(), "User Distance is -> " + arrNearbyStrangersResponse[0].getDistance(),
                //        Toast.LENGTH_LONG).show();

                //Toast.makeText(HomeActivity.getInstance(), "User ID is -> " + userID + "\n"
                //                + "longitude -> " + longitude + "\n" + "latitude ->" + latitude + "\n" + "Distance ->" + distance,
                //        Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(JOR);
        Toast.makeText(HomeActivity.getInstance(), "Arraylist size is -> " + rcObj.getUnsortedRC_List().size(),
                    Toast.LENGTH_LONG).show();

        return rcList;
    }

    public void ProfileRequest(String sUserID){
        //Toast.makeText(HomeActivity.getInstance(), "Total Number of Profiles are " + iNumProfiles,
        //        Toast.LENGTH_LONG).show();

            //String sUserID = Integer.toString(arrNearbyStrangersResponse[i].getUserID());
            //String sProfileReq = "https://www.eternalvibes.me/getuserinfo/" + 303346716;
            String sGetProfileReq = "https://www.eternalvibes.me/getuserinfo/" + sUserID;

            JsonArrayRequest arrJOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sGetProfileReq, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray responseArray) {
                    Toast.makeText(HomeActivity.getInstance(), "Profile # " + counter + " -> " + responseArray.toString(),
                               Toast.LENGTH_LONG).show();
                    counter++;
                    rcObj.getUnsortedRC_List().add(new RadarContent(responseArray));
                    //rcList.add(new RadarContent(responseArray));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(arrJOR);
    }//end of ProfileRequest method

}//end of class
