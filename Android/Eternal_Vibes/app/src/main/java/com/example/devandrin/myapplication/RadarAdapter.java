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
        radarViewComponents radarComponents;

        if (convertView == null)  {
            radarComponents = new radarViewComponents();
            //Creates the required components from scratch for the first time a user sees the Radar
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.radar_item, parent, false);
            radarComponents.txtViewUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            radarComponents.txtViewDistance = (TextView) convertView.findViewById(R.id.txtDistance);
            radarComponents.txtViewLocation = (TextView) convertView.findViewById(R.id.txtLocation);
            radarComponents.txtTimestamp = (TextView) convertView.findViewById(R.id.txtTimestamp);
            radarComponents.txtRating = (TextView) convertView.findViewById(R.id.txtRating);
            radarComponents.txtRanking = (TextView) convertView.findViewById(R.id.txtRanking);
            convertView.setTag(radarComponents);
        }
        else {
            //Otherwise, keep recycling the existing components for better performance instead of re-creating
            radarComponents = (radarViewComponents) convertView.getTag();
        }

        radarComponents.txtViewUsername.setText(RC.getsUsername());
        radarComponents.txtViewDistance.setText(RC.getDistance() + " km away");
        radarComponents.txtViewLocation.setText("in : " + RC.getsLocation());
        radarComponents.txtTimestamp.setText("Time : " + RC.getTimeStamp() + "");
        radarComponents.txtRating.setText("Rating : " + RC.getRating() + "/5");
        radarComponents.txtRanking.setText("Rank : " + RC.getRanking());

        return convertView;
    }

    //A class to hold Android components
    private static class radarViewComponents{
        private TextView txtViewUsername;
        private TextView txtViewDistance;
        private TextView txtViewLocation;
        private TextView txtTimestamp;
        private TextView txtRating;
        private TextView txtRanking;
    }

}
