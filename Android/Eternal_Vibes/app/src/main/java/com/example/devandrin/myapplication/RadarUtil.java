package com.example.devandrin.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content {

    //Use two different arraylists to prevent
    private static ArrayList<RadarContent> unsorted_radarList = new ArrayList<>();
    private static ArrayList<RadarContent> sorted_radarList;
    private static RadarAdapter raObj;
    private static View view;
    public static boolean isProfileSelected = false;

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public static void UpdatedSort_RadarProfiles(String RadarSortType, Boolean isAscending) {

        if(unsorted_radarList == null){
            unsorted_radarList = new ArrayList<>();
            unsorted_radarList.add(new RadarContent(3528, "James", "Vrom", 11, 4, "Intermediate", "Rosebank", 4200, "James99@gmail.com"));
        }

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

        setupRadar(view);
    }

    //Setup the variables to display the resulting sorted profiles
    private static void setupRadar(View view) {

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
            }
        });

        raObj.notifyDataSetChanged();
    }

    /**
     * @return a custom view to display content
     */
    @Override
    public View displayContent() {
        view = super.displayContent();

        //The setup profiles method has been moved to the RadarThread class
        unsorted_radarList = HomeActivity.getInstance().getRadarThreadObj().getUnsorted_radarList();
        if(unsorted_radarList == null){
            Snackbar s = Snackbar.make(HomeActivity.getInstance().findViewById(R.id.cLayout), "Could locate other users due to a connection error.", Snackbar.LENGTH_LONG);
            s.show();
        }
        UpdatedSort_RadarProfiles("DISTANCE", false); //Default sort by shortest distance

        return view;
    }

}//end of class
