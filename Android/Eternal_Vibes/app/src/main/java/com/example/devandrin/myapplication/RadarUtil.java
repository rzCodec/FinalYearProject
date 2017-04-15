package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class RadarUtil extends Content
{

    public RadarUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.radar_frag, container, false);
        TextView temp = (TextView) view.findViewById(R.id.rf_profile_name);
        temp.setText("PrinceNMen");
        temp = (TextView) view.findViewById(R.id.rf_timestamp);
        temp.setText("11:23 Wednesday");
        temp = (TextView) view.findViewById(R.id.rf_distance);
        temp.setText("1 KM");
        temp = (TextView) view.findViewById(R.id.rf_profile_name1);
        temp.setText("Dvdk01");
        temp = (TextView) view.findViewById(R.id.rf_timestamp1);
        temp.setText("11:13 Wednesday");
        temp = (TextView) view.findViewById(R.id.rf_distance1);
        temp.setText("3.6 KM");
        temp = (TextView) view.findViewById(R.id.rf_profile_name2);
        temp.setText("SUperNull");
        temp = (TextView) view.findViewById(R.id.rf_timestamp2);
        temp.setText("10:51 Wednesday");
        temp = (TextView) view.findViewById(R.id.rf_distance2);
        temp.setText("5.6 KM");
        temp = (TextView) view.findViewById(R.id.rf_profile_name3);
        temp.setText("SnotKip");
        temp = (TextView) view.findViewById(R.id.rf_timestamp3);
        temp.setText("10:30 Wednesday");
        temp = (TextView) view.findViewById(R.id.rf_distance3);
        temp.setText("1.2 KM");
        return view;
    }
}
