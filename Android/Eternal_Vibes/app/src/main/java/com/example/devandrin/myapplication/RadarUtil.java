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

    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }
    private static ArrayList<RadarContent> radarList;
    @Override
    public View displayContent() {
        View view = super.displayContent();
         radarList = new ArrayList<>();

        radarList.add(new RadarContent("Bob", "8km", "Sandton", 500));
        radarList.add(new RadarContent("Tom", "5km", "Randburg", 1500));
        radarList.add(new RadarContent("Jane", "21km", "Rosebank", 850));

        RadarAdapter raObj = new RadarAdapter(HomeActivity.getInstance().getApplicationContext(), radarList);
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        lv.setAdapter(raObj);
        return view;
    }
}
