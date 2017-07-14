package com.example.devandrin.myapplication;

import android.preference.ListPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content {

    //Use two different arraylists to prevent clashing
    ArrayList<RadarContent> unsorted_radarList = new ArrayList<>();
    ArrayList<RadarContent> sorted_radarList = new ArrayList<>();
    RadarContent[] arrAPI_Profiles = new RadarContent[5];

    /**
     * @param inflater - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    /**
     * @return a custom view to display content
     */
    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        setupProfiles();
        sortProfiles("DISTANCE", true);
        setupRadar(view);

        return view;
    }

    /**
     * Sends a request and receives an array of profiles from the API response
     * Then calculates the distance of each user to check if they are within range
     */
    private void setupProfiles(){

        //Dummy profiles are used until the API to return actual profiles is implemented
        arrAPI_Profiles[0] = new RadarContent(452, "Jessica", 12, 2, "Beginner", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob", 20, 3, "Intermediate", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom", 18, 5, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Dr. T", 40, 1, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Mr. K", 34, 4, "Master", "Soweto", 800);

        int iSearchRadius = 50;

        for(int i = 0; i < arrAPI_Profiles.length; i++){
            if(arrAPI_Profiles[i].getDistance() <= iSearchRadius) {
                unsorted_radarList.add(arrAPI_Profiles[i]);
            }
        }
    }

    //Sort the profiles based on the user's choice using a prioriy queue
    private void sortProfiles(String sortType, Boolean isAscending){

        ProfileQueue pqObj = new ProfileQueue(unsorted_radarList);
        pqObj.ProfileSort(sortType, isAscending);

        while(pqObj.iterator().hasNext()){
            sorted_radarList.add(pqObj.poll());
            //Add the sorted array into an array list so it can be displayed
        }

    }

    //Setup the variables to display the resulting sorted profiles
    private void setupRadar(View view){

        RadarAdapter raObj = new RadarAdapter(HomeActivity.getInstance().getApplicationContext(), sorted_radarList);
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        lv.setAdapter(raObj);
    }

}//end of class
