package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class MessengerUtil extends Content {
    private final static String[] CONTENT = {"Joe", "Dv", "Nola", "Jimmy"};

    public MessengerUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ArrayList<Chat> clist = new ArrayList<>();
        clist.add(new Chat(11, "Han Solo", "Where you at Bro??"));
        clist.add(new Chat(13, "NewTon", "I hope you become a null"));
        clist.add(new Chat(14, "NewTon", "I hope you become a null"));
        clist.add(new Chat(15, "NewTon", "I hope you become a null"));
        clist.add(new Chat(16, "NewTon", "I hope you become a null"));
        clist.add(new Chat(17, "NewTon", "I hope you become a null"));
        clist.add(new Chat(18, "NewTon", "I hope you become a null"));
        clist.add(new Chat(19, "NewTon", "I hope you become a null"));
        MessengerAdapter ma = new MessengerAdapter(HomeActivity.getInstance().getApplicationContext(), clist);
        ListView l = (ListView) view.findViewById(R.id.ArrayList);
        l.setAdapter(ma);
        return view;
    }
}
