package com.example.devandrin.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/15/2017.
 */

public class RadarProfileEventListAdapter extends ArrayAdapter<EventItem> {
    public RadarProfileEventListAdapter(Context context, ArrayList<EventItem> radarProfileEventList) {
        super(context, R.layout.radar_profile_event_item, radarProfileEventList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final EventItem eventItem = getItem(position);
        ComponentContainer cc;

        if (convertView == null)  {
            cc = new ComponentContainer();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.radar_profile_event_item, parent, false);
            cc.txtEventName = (TextView) convertView.findViewById(R.id.radarProfileEventName);
            cc.txtEventDescription = (TextView) convertView.findViewById(R.id.radarProfileDescription);
            cc.txtSkillsRequired = (TextView) convertView.findViewById(R.id.radarProfileSkillsetRequired);
            convertView.setTag(cc);
        }
        else {
            //Otherwise, keep recycling the existing components for better performance instead of re-creating
            cc = (ComponentContainer) convertView.getTag();
        }

        cc.txtEventName.setTextColor(Color.BLACK);
        cc.txtEventName.setTextSize(18);
        cc.txtEventName.setText("Event Name : " + eventItem.getName());

        cc.txtEventDescription.setTextColor(Color.BLACK);
        cc.txtEventDescription.setText("Description : " + eventItem.getInfo());

        cc.txtSkillsRequired.setTextColor(Color.BLACK);
        cc.txtSkillsRequired.setText("Musician Skills Required : " + eventItem.getSkillsRequired() + "\n");

        return convertView;
    }

    private class ComponentContainer{
        private TextView txtEventName;
        private TextView txtEventDescription;
        private TextView txtSkillsRequired;
    }
}
