package com.example.devandrin.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content {

    //Use two different arraylists to prevent
    private static ArrayList<RadarContent> unsorted_radarList = new ArrayList<>();
    private static ArrayList<RadarContent> sorted_radarList;
    private static RadarAdapter raObj;
    private static View view;
    private RadarContent[] arrAPI_Profiles;
    public static boolean isProfileSelected = false;

    //=============
    private static ArrayList<RadarContent> rcList;
    private static RadarContent[] arrNearbyStrangersResponse;
    private static int counter = 0;

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    //==================================================================================================

    public static void makeRequest(String activeuserID){

        String sReq = "https://www.eternalvibes.me/getNearbyStrangers/" + activeuserID;
        JsonArrayRequest JOR = new JsonArrayRequest(JsonArrayRequest.Method.GET, sReq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                arrNearbyStrangersResponse = new RadarContent[responseArray.length()];
                String userID = "";
                double longitude = 0;
                double latitude = 0;
                int distance = 0;

                //raObj.clear();

                Toast.makeText(HomeActivity.getInstance(), "Details" + responseArray.toString(),
                        Toast.LENGTH_LONG).show();

                /*
                for(int i = 0; i < responseArray.length(); i++){
                    try{
                        //The response array is in the following JSON format
                        /*
                         * [{"user"{"id", "longitude", "latitude"} "km"}, etc]

                        JSONObject jOuterObj = responseArray.getJSONObject(i);
                        JSONObject innerObj = jOuterObj.getJSONObject("user");
                        userID = innerObj.getString("id");
                        longitude = innerObj.getDouble("longitude");
                        latitude = innerObj.getDouble("latitude");
                        distance = (int) jOuterObj.getDouble("km");

                        rcList = ProfileRequest(userID, distance);
                        //arrNearbyStrangersResponse[i] = new RadarContent(Integer.parseInt(userID), longitude, latitude, distance);

                        //Toast.makeText(HomeActivity.getInstance(), "Profile # " + i + "\n" + arrNearbyStrangersResponse[i].getNearbyStrangersResponseString(),
                        //        Toast.LENGTH_LONG).show();


                    }
                    catch(JSONException e){
                        Log.e("Volley", "Error from getNearbyStrangersAPI" + e.toString());
                    }
                }//end for loop*/

                raObj.addAll(rcList);
                raObj.notifyDataSetChanged();

                //Toast.makeText(HomeActivity.getInstance(), "Size is -> " + rcList.size(),
                //      Toast.LENGTH_LONG).show();


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
    }

    //==================================================================================================

    private static ArrayList<RadarContent> ProfileRequest(String sUserID, final int iDistance){
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

                RadarContent rcObj = new RadarContent(responseArray);
                rcObj.setDistance(iDistance);
                rcObj.setRanking("<Ranking will go here>");
                rcObj.setRating(new Random().nextInt(5) + 1);
                rcObj.setsEmail("temp@gmail.com");
                rcObj.setSkillset("<Skills will go here>");
                rcObj.setsLocation("<Location will go here>");

                rcList.add(rcObj);

                //rcObj.getUnsortedRC_List().add(new RadarContent(responseArray));
                //rcList.add(new RadarContent(responseArray));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(arrJOR);
        return rcList;
    }//end of ProfileRequest method




    //==================================================================================================

    public static void UpdatedSort_RadarProfiles(String RadarSortType, Boolean isAscending) {
        if(unsorted_radarList == null){
            unsorted_radarList = new ArrayList<>();
            //unsorted_radarList.add(new RadarContent(3528, "James", "Vrom", 11, 4, "Intermediate", "Rosebank", 4200, "James99@gmail.com", "Drums"));
        }

        unsorted_radarList = HomeActivity.getInstance().returnRadarContentInstance().getUnsortedRadarList();
        //unsorted_radarList = HomeActivity.getInstance().getRadarThreadObj().getUnsorted_radarList();
        ProfileQueue pqObj = new ProfileQueue(unsorted_radarList);
        pqObj.ProfileSort(RadarSortType, isAscending);

        //Each time the items in the Radar are sorted,
        //it must do it with a brand new list and not an existing one
        sorted_radarList = new ArrayList<>();

        while (pqObj.iterator().hasNext()) {
            sorted_radarList.add(pqObj.poll());
            //Remove each Profile from the queue with the highest priority then
            //add each profile to an arraylist so it can displayed in the RadarAdapter class
        }

        if(raObj != null){
            raObj.clear();
            raObj.addAll(sorted_radarList);
            raObj.notifyDataSetChanged();
        }
        //setupRadar(view);
    }

    //Setup the variables to display the resulting sorted profiles
    private static void setupRadar(View view) {

        if(sorted_radarList == null){
            sorted_radarList = new ArrayList<RadarContent>();
        }

        raObj = new RadarAdapter(HomeActivity.getInstance().getApplicationContext(), sorted_radarList);
        final ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        lv.setAdapter(raObj);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method allows an item in a listview to be clicked.
             * Various actions or activities can be created from here.
             * @param adapter
             * @param view
             * @param position
             * @param arg
             */
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                //When the user clicks an item in the listview, pass that specific object data
                //into the home activity, setup a brand new radar content object and send the data to
                //to the RadarProfileActivity class
                RadarContent rcObj = (RadarContent) adapter.getItemAtPosition(position);
                HomeActivity.getInstance().setRadarProfileObject(rcObj);
                HomeActivity.getInstance().setupRadarProfileMenu(lv);
                Toast.makeText(HomeActivity.getInstance(), "Size of unsorted list is -> " + HomeActivity.getInstance().getUnsortedRadarList().size() + "\n"
                        + "First name is -> " + HomeActivity.getInstance().getUnsortedRadarList().get(1).getsUsername(),
                        Toast.LENGTH_LONG).show();
            }
        });

        //raObj.notifyDataSetChanged();
    }

    /**
     * @return a custom view to display content
     */
    @Override
    public View displayContent() {
        view = super.displayContent();
        //The setup profiles method has been moved to the RadarThread class
        //unsorted_radarList = HomeActivity.getInstance().getRadarThreadObj().getUnsorted_radarList();
        //setupProfiles();
        //unsorted_radarList = HomeActivity.getInstance().getUnsortedRadarList();
        if(unsorted_radarList == null){
            unsorted_radarList = new ArrayList<RadarContent>();
            Snackbar s = Snackbar.make(HomeActivity.getInstance().findViewById(R.id.cLayout), "Could locate other users due to a connection error.", Snackbar.LENGTH_LONG);
            s.show();
        }
        setupRadar(view);
        UpdatedSort_RadarProfiles("DISTANCE", false); //Default sort by shortest distance
        return view;
    }

    /**
     * Sends a request and receives an array of profiles from the API response
     * Then calculates the distance of each user to check if they are within range
     */
    private void setupProfiles(){

        //Dummy profiles are used for now until the API to return actual profiles is implemented
        int numProfiles = 3;
        arrAPI_Profiles = new RadarContent[numProfiles];

        arrAPI_Profiles[0] = new RadarContent(303346736, "John", "Crester", 12, 3, "Intermediate", "Sandton", 2100, "Jess55@gmail.com", "Vocals");
        arrAPI_Profiles[1] = new RadarContent(303346737, "mrBob", "Breck", 20, 2, "Beginner", "Houghton", 500, "bob2@gmail.com", "Drums");
        arrAPI_Profiles[2] = new RadarContent(303346740, "Vanneh", "Kunni", 18, 4, "Advanced", "Randburg", 1500, "201320596@student.uj.ac.za", "Keyboard");

        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one
        unsorted_radarList = new ArrayList<>();
        for(int i = 0; i < arrAPI_Profiles.length; i++){
            unsorted_radarList.add(arrAPI_Profiles[i]);
        }
    }

    @Override
    protected void update() {

    }
}//end of class
