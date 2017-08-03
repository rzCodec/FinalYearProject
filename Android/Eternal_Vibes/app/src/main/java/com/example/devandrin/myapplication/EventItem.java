package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventItem {
    private String Name;
    private String Info;
    private long Date;

    public EventItem(String name, String info, long date) {
        Name = name;
        Info = info;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }
}
