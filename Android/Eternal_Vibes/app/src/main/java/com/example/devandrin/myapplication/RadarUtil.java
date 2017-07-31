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

        //setupProfiles();
        unsorted_radarList = HomeActivity.getInstance().getRadarThreadObj().getUnsorted_radarList();
        UpdatedSort_RadarProfiles("DISTANCE", false); //Default sort by shortest distance

        return view;
    }

    /**
     * Sort the radar profiles according to the user's selection from a context menu in HomeActivity
     * @param RadarSortType - Distance, Rating or Rank
     * @param isAscending - True or False
     */



    private void setupProfiles() {
        //Dummy profiles are used for now until the API to return actual profiles is implemented
        arrAPI_Profiles[0] = new RadarContent(452, "Jessica Grom", 12, 3, "Intermediate", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob Brown", 20, 2, "Beginner", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom Yeis", 18, 4, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany Vlein", 40, 5, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry Alko", 34, 1, "Beginner", "Soweto", 800);

        //Calculate distance will be added soon

        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one
        unsorted_radarList = new ArrayList<>();
        for (int i = 0; i < arrAPI_Profiles.length; i++) {
            if (arrAPI_Profiles[i].getDistance() <= 45) {
                unsorted_radarList.add(arrAPI_Profiles[i]);
            }
        }
    }

}//end of class
