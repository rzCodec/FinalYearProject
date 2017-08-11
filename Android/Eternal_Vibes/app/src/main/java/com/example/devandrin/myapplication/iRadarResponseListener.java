package com.example.devandrin.myapplication;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/10/2017.
 */

public interface iRadarResponseListener{
    public ArrayList<RadarContent> extractNearbyStrangersData(JSONArray jsonResponseArray);
}