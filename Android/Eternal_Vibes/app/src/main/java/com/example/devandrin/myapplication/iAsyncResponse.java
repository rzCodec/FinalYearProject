package com.example.devandrin.myapplication;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/12/2017.
 */

public interface iAsyncResponse{
    public ArrayList<RadarContent> processCompleted(ArrayList<RadarContent> rcList);
}