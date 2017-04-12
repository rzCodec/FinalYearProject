package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Devandrin on 2017/04/01.
 */

public abstract class RenderFactory
{
    public static iContent getClass(String s, LayoutInflater inflater, ViewGroup container)
    {

        if(s.contains("Messenger"))
        {
            return new MessengerUtil(inflater,container);
        }
        else if(s.contains("NewsFeed"))
        {
            return new NewsFeedUtil(inflater,container);
        }
        else if(s.contains("Radar"))
        {
            return new RadarUtil(inflater,container);
        }
        return null;
    }
    public static View getListView(LayoutInflater i, ViewGroup c)
    {
        return i.inflate(R.layout.list_fragment,c,false);
    }

}
