package com.example.devandrin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ronald on 8/16/2017.
 */

public class RadarEventAsyncTask extends AsyncTask<String, Integer, ArrayList<EventItem>> {

    private ArrayList<EventItem> eventItemResponseList = new ArrayList<>();
    private RadarProfileEventListAdapter radarEventListAdapter;
    private ProgressDialog progressDialog;

    public RadarEventAsyncTask(RadarProfileEventListAdapter radarEventListAdapter, Context context){
        this.radarEventListAdapter = radarEventListAdapter;
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setTitle("Profile and Event List Information");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected ArrayList<EventItem> doInBackground(String... paramURL){
        String requestUrl = paramURL[0];
        //Make the JSON Request here and send the result to onPostExecute
        RadarRequest radarRequest = new RadarRequest();
        eventItemResponseList = radarRequest.extractCurrentUserEventList(requestUrl, radarEventListAdapter);
        try{
            for(int i = 0; i < 100; i++){
                Thread.sleep(25);
                publishProgress(i);
            }
        }
        catch(InterruptedException ie){

        }
        return eventItemResponseList;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values[0]);
        progressDialog.setProgress(values[0]);

    }

    @Override
    protected void onPostExecute(ArrayList<EventItem> eventItemList){
        super.onPostExecute(eventItemList);
        this.eventItemResponseList = eventItemList;
        progressDialog.dismiss();
    }
}
