package com.example.devandrin.myapplication;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    private static ArrayList<NewsFeedItem> arrData;
    private static NewsFeedAdapter adapter;

    public NewsFeedUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public static void makeRequest(String id) {
        HomeActivity.getInstance().enableProgressBar();
        String Url = "https://www.eternalvibes.me/getstatuses/" + id;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                adapter.clear();
                arrData = NewsFeedItem.ExtractData(response);

                adapter.addAll(arrData);
                adapter.notifyDataSetChanged();
                HomeActivity.getInstance().disableProgressBar();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        HomeActivity.getInstance().disableProgressBar();
                        Log.d("NewsFeedRequest", "onErrorResponse: " + error.getMessage());
                        Snackbar s = Snackbar.make(HomeActivity.getInstance().findViewById(R.id.cLayout), "Connection Error", Snackbar.LENGTH_INDEFINITE);
                        s.setAction("Try Again", retryCall());
                        s.show();
                    }
                });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(jar);
    }

    private static View.OnClickListener retryCall() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.getInstance().refreshNewsFeed();
            }
        };
    }

    @Override
    public View displayContent() {
        View view = super.displayContent();
        ListView lv = (ListView) view.findViewById(R.id.ArrayList);
        if (arrData == null) {
            arrData = new ArrayList<>();
        }
        adapter = new NewsFeedAdapter(HomeActivity.getInstance().getApplicationContext(), arrData);
        lv.setAdapter(adapter);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        srl.setOnRefreshListener(srfListener());
        return view;
    }

    @Override
    protected void update() {
        HomeActivity.getInstance().refreshNewsFeed();
        srl.setRefreshing(false);

    }
}
