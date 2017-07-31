package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content {

    //Use two different arraylists to prevent clashing
    private static ArrayList<RadarContent> unsorted_radarList;
    private static ArrayList<RadarContent> sorted_radarList;
    private static RadarAdapter raObj;
    private static View view;
    private RadarContent[] arrAPI_Profiles = new RadarContent[5];

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public static void UpdatedSort_RadarProfiles(String RadarSortType, Boolean isAscending) {

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
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        lv.setAdapter(raObj);
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
        UpdatedSort_RadarProfiles("DISTANCE", false); //Default sort by shortest distance

        return view;
    }
}//end of class
