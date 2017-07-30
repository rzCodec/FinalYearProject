package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Devandrin on 2017/04/01.
 */

public abstract class RenderFactory {
    public static iContent getClass(String s, LayoutInflater inflater, ViewGroup container) {

        if (s.equals("Messenger")) {
            return new MessengerUtil(inflater, container);
        } else if (s.equals("NewsFeed")) {
            return new NewsFeedUtil(inflater, container);
        } else if (s.equals("Radar")) {
            return new RadarUtil(inflater, container);
        } else if (s.equals("Events")) {
            return new EventUtil(inflater, container);
        } else if (s.equals("PersonalEvents")) {
            return new PersonalEventUtil(inflater, container);
        }


        return null;
    }

    public static View getListView(LayoutInflater i, ViewGroup c) {
        return i.inflate(R.layout.list_fragment, c, false);
    }

}
