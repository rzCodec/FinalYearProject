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
    private static ArrayList<RadarContent> unsortedResponseList = new ArrayList<>();

    //private static int counter = 0;

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    //==================================================================================================

    public static void UpdatedSort_RadarProfiles(String RadarSortType, Boolean isAscending) {
        if(unsorted_radarList == null){
            unsorted_radarList = new ArrayList<>();
            //unsorted_radarList.add(new RadarContent(3528, "James", "Vrom", 11, 4, "Intermediate", "Rosebank", 4200, "James99@gmail.com", "Drums"));
        }

        //unsorted_radarList = HomeActivity.getInstance().returnRadarContentInstance().getUnsortedRadarList();
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
        //setupRadar(view);
    }
    /**
     * @return a custom view to display content
     */
    @Override
    public View displayContent() {
        view = super.displayContent();
        final ListView lv = (ListView) view.findViewById(R.id.ArrayList);

        RadarRequest rrObj = new RadarRequest();
        String currentUserID = HomeActivity.getInstance().activeuserID;
        unsortedResponseList = rrObj.extractNearbyStrangersData(currentUserID);

        if(unsortedResponseList == null){
            unsortedResponseList = new ArrayList<>();
        }

        raObj = new RadarAdapter(HomeActivity.getInstance().getApplicationContext(), unsortedResponseList);
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
                RadarContent rcObj = (RadarContent) adapter.getItemAtPosition(position);
                HomeActivity.getInstance().setRadarProfileObject(rcObj);
                HomeActivity.getInstance().setupRadarProfileMenu(lv);
                //Toast.makeText(HomeActivity.getInstance(), "Size of rcList is " + radarAsyncTaskObj.getRadarResponseList().size(),
                 //     Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
    @Override
    protected void update() {

    }
}//end of class
