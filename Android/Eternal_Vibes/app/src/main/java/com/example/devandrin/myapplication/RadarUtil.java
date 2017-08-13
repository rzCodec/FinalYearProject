package com.example.devandrin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content {

    //Use two different arraylists to prevent
    //This is the response from the getnearbyStrangers API returns
    //Must be static because it accessed from other methods and classes
    private static ArrayList<RadarContent> unsortedResponseList;
    private static ArrayList<RadarContent> sorted_radarList;
    private static RadarAdapter radarAdapter;
    private static View view;
    public static ListView lv = null;

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    //==================================================================================================
    public static void UpdatedSort_RadarProfiles(String RadarSortType, Boolean isAscending) {
        ProfileQueue pqObj = new ProfileQueue(unsortedResponseList);
        pqObj.ProfileSort(RadarSortType, isAscending);
        //Each time the items in the Radar are sorted,
        //it must do it with a brand new list and not an existing one
        sorted_radarList = new ArrayList<>();
        while (pqObj.iterator().hasNext()) {
            sorted_radarList.add(pqObj.poll());
        }
        if(radarAdapter != null){
            radarAdapter.clear();
            radarAdapter.addAll(sorted_radarList);
            radarAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @return a custom view to display content
     */
    @Override
    public View displayContent() {
        view = super.displayContent();

        lv = (ListView) view.findViewById(R.id.ArrayList);
        //unsortedResponseList = HomeActivity.getInstance().getUnsortedRadarList();
        if(unsortedResponseList == null){
            unsortedResponseList = new ArrayList<>();
        }

        radarAdapter = new RadarAdapter(HomeActivity.getInstance().getApplicationContext(), unsortedResponseList);
        //The two below lines work as well
        //RadarRequest request = new RadarRequest();
        //request.extractNearbyStrangersData(HomeActivity.activeuserID, raObj);

        //Use an asynchronous task to make the request on a background thread instead of blocking the UI thread
        RadarAsyncTask requestTask = new RadarAsyncTask(HomeActivity.getInstance().getApplicationContext(), radarAdapter, lv);
        requestTask.execute(HomeActivity.activeuserID);

        lv.setAdapter(radarAdapter);
        radarAdapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method allows an item in a listview to be clicked.
             * Various actions or activities can be created from here.
             * @param adapter
             * @param view
             * @param position
             * @param arg
             *
             */
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                RadarContent rcObj = (RadarContent) adapter.getItemAtPosition(position);
                HomeActivity.getInstance().setRadarProfileObject(rcObj);
                HomeActivity.getInstance().setupRadarProfileMenu(lv);
                //Toast.makeText(HomeActivity.getInstance(), "Size of the unsorted list is " + unsortedResponseList.size(),
                //      Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    protected void update() {

    }
}//end of class
