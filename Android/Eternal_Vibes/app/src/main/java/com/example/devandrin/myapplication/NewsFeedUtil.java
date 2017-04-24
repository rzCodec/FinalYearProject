package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class NewsFeedUtil extends Content {
    private static ArrayList<NewsFeedItem> arrData ;
    private static NewsFeedAdapter adapter;
    public NewsFeedUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        if(arrData == null)
        {
            arrData = new ArrayList<>();
        }
        adapter=new NewsFeedAdapter(HomeActivity.getInstance().getApplicationContext(), arrData);
        lv.setAdapter(adapter);

        return view;
    }
    public static void makeRequest(String id)
    {
        String Url = "https://www.eternalvibes.me/getstatuses/"+id;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                adapter.clear();
                arrData = NewsFeedItem.ExtractData(response);

                adapter.addAll(arrData);
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("NewsFeedRequest", "onErrorResponse: "+error.getMessage());
                    }
                });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(jar);
    }
}
