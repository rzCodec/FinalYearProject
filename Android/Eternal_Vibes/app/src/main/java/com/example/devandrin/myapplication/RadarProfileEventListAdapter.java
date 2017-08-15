package com.example.devandrin.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/15/2017.
 */

public class RadarProfileEventListAdapter<EventItem> extends ArrayAdapter<EventItem> {
    public RadarProfileEventListAdapter(Context context, ArrayList<EventItem> radarProfileEventList) {
        super(context, 0, radarProfileEventList);
    }
}
