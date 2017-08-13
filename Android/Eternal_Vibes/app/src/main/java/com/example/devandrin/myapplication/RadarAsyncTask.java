package com.example.devandrin.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tagmanager.Container;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/11/2017.
 */

public class RadarAsyncTask extends AsyncTask<String, Void, ArrayList<RadarContent>> {

    //These fields are needed for testing, please do not delete
    private Context HomeActivityContext;
    private RadarAdapter radarAdapter;
    private ListView listview;
    private ArrayList<RadarContent> radarResponseList = new ArrayList<>();
    private iAsyncResponse delegateResponse = null;

    public RadarAsyncTask(){

    }

    public RadarAsyncTask(Context HomeActivityContext, RadarAdapter radarAdapter, ListView listview){
        this.HomeActivityContext = HomeActivityContext;
        this.radarAdapter = radarAdapter;
        this.listview = listview;
    }

    /**
     * Make use of the background thread to send a request
     * and receive a list of users within the current user's area
     * @param arractiveuserID
     * @return
     */
    @Override
    protected ArrayList<RadarContent> doInBackground(String... arractiveuserID){
        String activeuserID = arractiveuserID[0];
        RadarRequest radarRequest = new RadarRequest();
        radarResponseList = radarRequest.extractNearbyStrangersData(activeuserID, radarAdapter);
        return radarResponseList;
    }

    /**
     * Post the results to the UI thread when doInBackground is finished
     * @param
     */
    @Override
    protected void onPostExecute(ArrayList<RadarContent> rcList){
        this.radarResponseList = rcList;
    }

    //Methods below are for testing purposes, please do not delete
    public void setDelegateResponseListener(iAsyncResponse responseListener){
        this.delegateResponse = responseListener;
    }

    public ArrayList<RadarContent> getRadarResponseList(){
        return this.radarResponseList;
    }
}
