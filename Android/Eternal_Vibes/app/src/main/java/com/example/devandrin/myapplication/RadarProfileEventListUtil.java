package com.example.devandrin.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/16/2017.
 */

public class RadarProfileEventListUtil extends Content {
    private static ArrayList<EventItem> eventItemList = new ArrayList<>();
    private static RadarProfileEventListAdapter adapter;
    private RadarProfileActivity radarProfileActivityInstance = RadarProfileActivity.getInstance();
    private AlertDialog.Builder builder;

    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarProfileEventListUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent(){
        View view = super.displayContent();
        ListView listView = (ListView) view.findViewById(R.id.ArrayList);
        Context context = radarProfileActivityInstance.getApplicationContext();

        /*
        EventItem ei = new EventItem();
        ei.setName("Classic Event");
        ei.setInfo("I want to make a classic themed track. Looking for people");
        ei.setSkillsRequired("Drums, Vocals");

        EventItem ei2 = new EventItem();
        ei2.setName("Rap Collaboration");
        ei2.setInfo("I'm looking for someone with good beat mixing skills.");
        ei2.setSkillsRequired("Recorder, Beatmaker");

        eventItemList.add(ei);
        eventItemList.add(ei2);
        */
        adapter = new RadarProfileEventListAdapter(context, eventItemList);

        //Make the request for the current user's list of events that he/she has created
        //as well as events they are participating in
        String userID = Integer.toString(radarProfileActivityInstance.getRCObj().getUserID());
        RadarEventAsyncTask reaObj = new RadarEventAsyncTask(adapter, view.getContext());
        reaObj.execute(userID);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method allows an item in a listview to be clicked.
             * Various actions or activities can be created from here.
             * @param adapter
             * @param view
             * @param position
             * @param arg
             *
             */
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                EventItem eventItemObj = (EventItem) adapter.getItemAtPosition(position);
                String sAlias = radarProfileActivityInstance.radarContentObjToBePassed.getsAlias();
                String sEventName = eventItemObj.getName();
                createAlertBuilder(sAlias, sEventName);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    private void createAlertBuilder(String sAlias, final String sEventName){
        builder = new AlertDialog.Builder(RadarProfileActivity.getInstance());
        builder.setTitle(sAlias + "'s " + sEventName + " event");
        builder.setMessage("Send a request to join this event?");

        //The Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(radarProfileActivityInstance, "A request to be part of the " + sEventName + " event has been sent " , Toast.LENGTH_LONG).show();
            }
        });

        //The No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"You are now following " + sProfileName,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void update() {

    }
}
