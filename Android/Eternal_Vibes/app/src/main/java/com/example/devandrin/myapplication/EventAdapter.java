package com.example.devandrin.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventAdapter extends ArrayAdapter<EventItem>
{
    public EventAdapter( Context context, ArrayList<EventItem> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final EventItem item = getItem(position);
        viewComponents components;
        if(convertView == null)
        {
            components = new viewComponents();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
            components.name = (TextView)convertView.findViewById(R.id.eventName);
            components.info = (TextView)convertView.findViewById(R.id.eventInfo);
            convertView.setTag(components);
        }
        else
        {
            components = (viewComponents) convertView.getTag();
        }
        components.name.setText(item.getName());
        String info = item.getInfo();
        if(info.length() > 100)
        {
            components.info.setText(info.subSequence(0,100)+"...");
        }
        else
        {
            components.info.setText(info);
        }

        return convertView;
    }
    private static class viewComponents
    {
        TextView name;
        TextView info;
    }
}
