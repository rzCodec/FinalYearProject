package com.example.devandrin.myapplication;

import java.util.ArrayList;

/**
 * Created by Ronald on 7/26/2017.
 */

public class RadarThread implements Runnable {

    private ArrayList<RadarContent> unsorted_radarList;
    private RadarContent[] arrAPI_Profiles;
    private int distanceInMeters = 0;

    public RadarThread(){
        unsorted_radarList = new ArrayList<>();
    }

    @Override
    public void run(){

        android.os.Process.setThreadPriority(android.os.Process.THREAD.PRIORITY.BACKGROUND);
        String API_Request = "https://www.eternalvibes.me/getRadarUserList";
        arrAPI_Profiles[0] = new RadarContent(452, "Jessica Grom", 12, 3, "Intermediate", "Sandton", 2100);
        arrAPI_Profiles[1] = new RadarContent(89, "Bob Brown", 20, 2, "Beginner", "Houghton", 500);
        arrAPI_Profiles[2] = new RadarContent(1552, "Tom Yeis", 18, 4, "Advanced", "Randburg", 1500);
        arrAPI_Profiles[3] = new RadarContent(452, "Tiffany Vlein", 40, 5, "Master", "Pretoria", 4100);
        arrAPI_Profiles[4] = new RadarContent(842, "Jerry Alko", 34, 1, "Beginner", "Soweto", 800);

        //Calculate distance will be added soon

        //Each time the items in the Radar are sorted it must do it with a brand new list and not an existing one

        for(int i = 0; i < arrAPI_Profiles.length; i++){
            // Functionality will be added soon calcDistanceInMeters();
            if(arrAPI_Profiles[i].getDistance() <= 45) {
                unsorted_radarList.add(arrAPI_Profiles[i]);
            }
        }
    }

    /**
     * This calculates the distance in meters between two user's longitude and latitude coordinates
     * @return a value in meters
     */
    private int calcDistanceInMeters(){
        return 0;
    }

    public ArrayList<RadarContent> getUnsorted_radarList() {
        return unsorted_radarList;
    }
}
