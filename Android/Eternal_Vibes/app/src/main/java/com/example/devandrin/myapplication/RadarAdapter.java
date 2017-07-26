package com.example.devandrin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronnie on 2017-04-13.
 */

public class RadarAdapter extends ArrayAdapter<RadarContent> {
    public RadarAdapter(Context context, ArrayList<RadarContent> radarList) {
        super(context, R.layout.radar_item, radarList); //Pass in the custom layout file for the components
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final RadarContent RC = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.radar_item, parent, false);
        }

        //Find the textviews and set each textview's content with the information from the arrayList
        TextView txtViewUsername = (TextView) convertView.findViewById(R.id.txtUsername);
        txtViewUsername.setText(RC.getsUsername());

        TextView txtViewDistance = (TextView) convertView.findViewById(R.id.txtDistance);
        txtViewDistance.setText("Distance :" + RC.getsDistance());

        TextView txtViewLocation = (TextView) convertView.findViewById(R.id.txtLocation);
        txtViewLocation.setText("Location :" + RC.getsLocation());

        TextView txtTimestamp = (TextView) convertView.findViewById(R.id.txtTimestamp);
        txtTimestamp.setText("Time :" + RC.getTimeStamp() + "");

        return convertView;
    }

}
