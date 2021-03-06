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
import java.util.zip.Inflater;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class MessengerUtil extends Content {
    private static ArrayList<Chat> clist = null;

    private static MessengerAdapter adapter = null;
    public static void dataUpdate()
    {
        if(adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }
    public MessengerUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public static void makeRequest(String id) {
        String url = "https://eternalvibes.me/getchats/" + id;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() >= 0) {
                    clist = Chat.extractAll(response);
                    DBHelper dbh = HomeActivity.getDbHelper();
                    for (Chat c : clist) {
                        if(dbh != null)
                        {
                            dbh.insertChat(c);
                        }
                        ChatActivity.getMessages(c.ChatID);
                    }
                    if(adapter != null)
                    {
                        adapter.clear();
                        clist = dbh.getAllChats();
                        adapter.addAll(clist);
                        adapter.notifyDataSetChanged();
                    }
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

    @Override
    public View displayContent() {
        View view = super.displayContent();
        clist = new ArrayList<>();
        DBHelper dbh = HomeActivity.getDbHelper();
        clist = dbh.getAllChats();
        adapter = new MessengerAdapter(HomeActivity.getInstance().getApplicationContext(), clist);
        l = (ListView) view.findViewById(R.id.ArrayList);
        l.setAdapter(adapter);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        srl.setOnRefreshListener(srfListener());
        View Empty = inflater.inflate(R.layout.empty,null);
        l.setEmptyView(Empty);

        return view;
    }



    @Override
    protected void update() {
        HomeActivity.getInstance().refreshNewsFeed();
        srl.setRefreshing(false);
    }
}
