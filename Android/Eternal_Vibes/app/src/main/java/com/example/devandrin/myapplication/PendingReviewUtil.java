package com.example.devandrin.myapplication;

import android.app.usage.UsageEvents;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

/**
 * Created by Ronald on 7/26/2017.
 */

public class PendingReviewUtil extends Content {
    public PendingReviewUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    private static ArrayList<EventItem> dataList;
    private EventAdapter ea;

    @Override
    public View displayContent() {
        View v = super.displayContent();
        dataList = new ArrayList<EventItem>();
        dataList.add(new EventItem("This is a pending review", "Review: Yes or No", System.currentTimeMillis()));

        ListView lv = (ListView) v.findViewById(R.id.ArrayList);
        ea = new EventAdapter(EventActivity.getInstance().getApplicationContext(), dataList);
        lv.setAdapter(ea);
        return v;
    }


}