package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class NewsFeedUtil extends Content {
    public NewsFeedUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ArrayList<NewsFeedItem> arrTemp = HomeActivity.arrTemp;

        arrTemp.add(new NewsFeedItem(0, "Dvdk", 46148, "Im Over here !!", null, 3, 0));
        arrTemp.add(new NewsFeedItem(10, "DJ CJ", 6553, "Im Over There !!", null, 65, 2));
        arrTemp.add(new NewsFeedItem(23, "Ko321", 251125, "Im behind you !!", null, 23, 0));
        arrTemp.add(new NewsFeedItem(1, "Wannab3Guy", 125125, "I love Java!", null, 1, 30));
        arrTemp.add(new NewsFeedItem(56, "JoziL", 461251148, "Ain't no mountain high or low!!", null, 164, 0));
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        lv.setAdapter(new NewsFeedAdapter(HomeActivity.getInstance().getApplicationContext(), arrTemp));
        return view;
    }
}
