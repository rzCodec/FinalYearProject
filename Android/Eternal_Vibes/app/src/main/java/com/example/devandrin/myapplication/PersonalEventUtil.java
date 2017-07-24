package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/23.
 */

public class PersonalEventUtil extends Content {
    public PersonalEventUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    private static ArrayList<EventItem> dataList;
    private EventAdapter ea;
    @Override
    public View displayContent() {
        if(dataList == null)
            dataList = new ArrayList<>();
        dataList.add(new EventItem("Your events ", "Your own events will be displayed here",1120341241234L));
        dataList.add(new EventItem("Hello World","Hello World",System.currentTimeMillis()));
        dataList.add(new EventItem("Sentence", "The quick brown fox jumped over the lazy dog",109231245125L));
        View v = super.displayContent();
        ListView lv = (ListView)v.findViewById(R.id.ArrayList);
        ea = new EventAdapter(EventActivity.getInstance().getApplicationContext(),dataList);
        lv.setAdapter(ea);
        return v;
    }

    public static void setDataList(ArrayList<EventItem> dataList) {
        PersonalEventUtil.dataList = dataList;
    }
}
