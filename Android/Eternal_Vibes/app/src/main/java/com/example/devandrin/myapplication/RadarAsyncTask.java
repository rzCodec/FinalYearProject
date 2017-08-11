package com.example.devandrin.myapplication;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/11/2017.
 */

public class RadarAsyncTask extends AsyncTask<String, Void, ArrayList<RadarContent>> {

    public interface iAsyncResponse{
        public void processRadarProfileList(ArrayList<RadarContent> rcList);
    }

    private iAsyncResponse delegate;
    private ArrayList<RadarContent> radarResponseList = new ArrayList<>();
    private String hello = "Yo...";

    public RadarAsyncTask(){

    }

    @Override
    protected ArrayList<RadarContent> doInBackground(String... arractiveuserID){
        String activeuserID = arractiveuserID[0];

        //To be completed soon...

        return radarResponseList;
    }

    @Override
    protected void onPostExecute(ArrayList<RadarContent> rcList){
        delegate.processRadarProfileList(rcList);
    }

    public void setAsyncResponseListener(iAsyncResponse delegate){
        this.delegate = delegate;
    }

    public String returnHello(){
        return hello;
    }

    public ArrayList<RadarContent> getRadarResponseList(){
        return this.radarResponseList;
    }
}
