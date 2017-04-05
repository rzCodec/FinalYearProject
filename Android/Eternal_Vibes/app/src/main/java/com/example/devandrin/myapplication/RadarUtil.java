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
        TextView temp = (TextView) view.findViewById(R.id.rf)
        return view;
    }
}
