package com.example.devandrin.myapplication;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ronald on 8/10/2017.
 */

public class RadarRequest {
    private ArrayList<RadarContent> unsortedRadarResponseList = new ArrayList<>();
    public static String resString = "";

    public RadarRequest(){

    }

    /**
     * Make a request to find a list of muscians within the user's area
     * @param activeuserID - The current user id of the app
     * @return a list of user ids and distances
     */
    public ArrayList<RadarContent> extractNearbyStrangersData(String activeuserID, final RadarAdapter adapter){
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
                        unsortedRadarResponseList = extractUserInfo(userID, distance, adapter);
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }

                //Toast.makeText(HomeActivity.getInstance(), "List size is -> " + unsortedRadarResponseList.size(),
                //        Toast.LENGTH_LONG).show();

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
    private ArrayList<RadarContent> extractUserInfo(String sUserID, final int iDistance, final RadarAdapter adapter){
        String sGetProfileReq = "https://www.eternalvibes.me/getuserinfo/" + sUserID;

        JsonArrayRequest arrJOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sGetProfileReq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                //RadarContent rcObj = new RadarContent(RadarContent rcObj = new RadarContent(responseArray);
                RadarContent rcObj = new RadarContent();
                try{
                    JSONObject object = responseArray.getJSONObject(0);
                    rcObj.setsUsername(object.getString("firstname"));
                    rcObj.setsLastName(object.getString("surname"));
                    rcObj.setsAlias(object.getString("username"));
                    rcObj.setDistance(iDistance);
                    rcObj.setRanking("<Ranking will go here>");
                    rcObj.setRating(new Random().nextInt(5) + 1);
                    rcObj.setsEmail("temp@gmail.com");
                    rcObj.setSkillset("<Skills will go here>");
                    rcObj.setsLocation("<Location will go here>");
                }
                catch(Exception e){

                }
                unsortedRadarResponseList.add(rcObj);
                if(adapter != null){
                    adapter.clear();
                    adapter.addAll(unsortedRadarResponseList);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(arrJOR);
        return unsortedRadarResponseList;
    }

    //================ Temporary ===============

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();

        Toast.makeText(HomeActivity.getInstance(), "Response using Http is -> " + jsonString,
                Toast.LENGTH_LONG).show();

        //System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

}//end of class
