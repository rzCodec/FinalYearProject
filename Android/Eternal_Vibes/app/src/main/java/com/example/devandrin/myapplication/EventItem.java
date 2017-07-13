package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventItem {
    private String Name;
    private String Info;

    public EventItem(String name, String info) {
        Name = name;
        Info = info;
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
}
