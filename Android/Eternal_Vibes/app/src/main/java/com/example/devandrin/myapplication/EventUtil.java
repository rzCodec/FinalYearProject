package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventUtil extends Content {
    private static ArrayList<EventItem> dataList;
    private EventAdapter ea;
    public EventUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        //if(dataList == null)
        dataList = new ArrayList<>();
        dataList.add(new EventItem("Hello World", "Hello World", System.currentTimeMillis()));
        dataList.add(new EventItem("Sentence", "The quick brown fox jumped over the lazy dog", 109231245125L));
        dataList.add(new EventItem("Name", "Info 12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890", 1120341241234L));
        View v = super.displayContent();
        ListView lv = (ListView) v.findViewById(R.id.ArrayList);
        ea = new EventAdapter(EventActivity.getInstance().getApplicationContext(), dataList);
        lv.setAdapter(ea);
        return v;
    }

    public void MakeRequest(String ID) {
        //DOes request and update data
    }
}
