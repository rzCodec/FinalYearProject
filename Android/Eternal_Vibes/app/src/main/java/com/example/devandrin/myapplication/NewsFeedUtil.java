package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class NewsFeedUtil extends Content
{
    public NewsFeedUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }
    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.news_feed_frag,container,false);
        TextView temp = (TextView)view.findViewById(R.id.nf_profile_name);
        temp.setText("BoomSinger");
        temp = (TextView)view.findViewById(R.id.nfi_status);
        temp.setText("WASSSSUP!!!");
        temp = (TextView)view.findViewById(R.id.rf_timestamp);
        temp.setText("09:49 Wednesday");
        temp = (TextView)view.findViewById(R.id.nf_profile_name1);
        temp.setText("SongKiller");
        temp = (TextView)view.findViewById(R.id.nfi_status1);
        temp.setText("GO DIE!!!");
        temp = (TextView)view.findViewById(R.id.nf_timestamp1);
        temp.setText("09:55 Wednesday");
        temp = (TextView)view.findViewById(R.id.nf_profile_name2);
        temp.setText("NewTon");
        temp = (TextView)view.findViewById(R.id.nfi_status2);
        temp.setText("Glory glory Man United!!!");
        temp = (TextView)view.findViewById(R.id.nf_timestamp2);
        temp.setText("10:21 Wednesday");
        temp = (TextView)view.findViewById(R.id.nf_profile_name3);
        temp.setText("Han Solo");
        temp = (TextView)view.findViewById(R.id.nfi_status3);
        temp.setText("Same shit, Different Day...");
        temp = (TextView)view.findViewById(R.id.nf_timestamp3);
        temp.setText("10:41 Wednesday");

        return  view;
    }
}
