package com.example.devandrin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tagmanager.Container;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/11/2017.
 */

public class RadarAsyncTask extends AsyncTask<String, Integer, ArrayList<RadarContent>> {

    //These fields are needed for testing, please do not delete
    private Context HomeActivityContext;
    private View view;
    private ProgressDialog pd;
    private RadarAdapter radarAdapter;
    private ListView listview;
    private ArrayList<RadarContent> radarResponseList = new ArrayList<>();
    private iAsyncResponse delegateResponse = null;

    public RadarAsyncTask(){

    }

    public RadarAsyncTask(View view, Context HomeActivityContext, RadarAdapter radarAdapter, ListView listview){
        this.view = view;
        this.HomeActivityContext = HomeActivityContext;
        this.radarAdapter = radarAdapter;
        this.listview = listview;
    }

    /**
     * This method is used to setup the required components, variables, etc
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*
            pd = new ProgressDialog(view.getContext());
            pd.setTitle("Connect with nearby musicians");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMax(100);
            pd.setMessage("Loading");
            pd.show();*/
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
        RadarRequest radarRequest = new RadarRequest(this);
        radarResponseList = radarRequest.extractNearbyStrangersData(activeuserID, radarAdapter);

        /*
            try{
                for(int i = 0; i < 100; i++){
                    Thread.sleep(35);
                    publishProgress(i);
                }
            }
            catch(InterruptedException ie){

            }*/

        return radarResponseList;
    }

    /**
     * Show the progress to the user interface
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //pd.setProgress(values[0]);
    }

    /**
     * Post the results to the UI thread when doInBackground is finished
     * @param
     */
    @Override
    protected void onPostExecute(ArrayList<RadarContent> rcList){
        this.radarResponseList = rcList;
        //HomeActivity.getInstance().disableProgressBar();
        //pd.dismiss();
    }

    //Methods below are for testing purposes, please do not delete
    public void setDelegateResponseListener(iAsyncResponse responseListener){
        this.delegateResponse = responseListener;
    }

    public ArrayList<RadarContent> getRadarResponseList(){
        return this.radarResponseList;
    }
}
