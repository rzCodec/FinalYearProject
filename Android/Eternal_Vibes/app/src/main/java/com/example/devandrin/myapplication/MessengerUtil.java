package com.example.devandrin.myapplication;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
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
 * Created by Devandrin on 2017/04/01.
 */

public class MessengerUtil extends Content {
    private static ArrayList<Chat> clist = null;

    private static MessengerAdapter adapter = null;
    public MessengerUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = super.displayContent();
        clist = new ArrayList<>();
        DBHelper dbh = HomeActivity.getDbHelper();
        clist = dbh.getAllChats();
        adapter = new MessengerAdapter(HomeActivity.getInstance().getApplicationContext(), clist);
        ListView l = (ListView) view.findViewById(R.id.ArrayList);
        l.setAdapter(adapter);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        srl.setOnRefreshListener(srfListener());
        return view;
    }

    public static void makeRequest(String id)
    {
        String url = "https://eternalvibes.me/getchats/"+id;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() <=0)
                {
                    HomeActivity.getInstance().onResume();
                }
                else
                {
                    clist = Chat.extractAll(response);
                    DBHelper dbh = HomeActivity.getDbHelper();
                    for (Chat c : clist)
                    {
                        dbh.insertChat(c);
                    }
                    adapter.clear();
                    clist = dbh.getAllChats();
                    adapter.addAll(clist);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do nothing
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
}
