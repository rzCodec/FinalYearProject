package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventUtil extends Content{
    public EventUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }
    ArrayList<EventItem> dataList;
    EventAdapter ea;
    @Override
    public View displayContent() {
        if(dataList == null)
        dataList = new ArrayList<>();
        dataList.add(new EventItem("Hello World","Hello World"));
        dataList.add(new EventItem("Sentence", "The quick brown fox jumped over the lazy dog"));
        dataList.add(new EventItem("Name", "Info"));
        View v = inflater.inflate(R.layout.list_fragment,container,false);
        ListView lv = (ListView)v.findViewById(R.id.ArrayList);
        ea = new EventAdapter(EventActivity.getInstance().getApplicationContext(),dataList);
        lv.setAdapter(ea);
        return v;
    }
}
