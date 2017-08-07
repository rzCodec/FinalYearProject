package com.example.devandrin.myapplication;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventUtil extends Content {
    private static ArrayList<EventItem> dataList;
    private static EventAdapter ea;
    public EventUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        //if(dataList == null)
        dataList = new ArrayList<>();
        View v = super.displayContent();
        ListView lv = (ListView) v.findViewById(R.id.ArrayList);
        ea = new EventAdapter(EventActivity.getInstance().getApplicationContext(), dataList);
        lv.setAdapter(ea);
        srl = (SwipeRefreshLayout) v.findViewById(R.id.refreshView);
        srl.setOnRefreshListener(srfListener());
        return v;
    }

    @Override
    protected void update() {
        EventActivity.getInstance().onResume();
        srl.setRefreshing(false);
    }

    public static void makeRequest(int UserId)
    {
        EventActivity.getInstance().enableProgressBar();
        String url = "https://www.eternalvibes.me/getevents/"+UserId;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                EventActivity.getInstance().disableProgressBar();
                ArrayList<EventItem> temp = EventItem.fromJSONArray(response);
                if(temp != null)
                {
                    ea.clear();
                    dataList = temp;
                    ea.addAll(dataList);
                    ea.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventActivity.getInstance().disableProgressBar();
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
}
