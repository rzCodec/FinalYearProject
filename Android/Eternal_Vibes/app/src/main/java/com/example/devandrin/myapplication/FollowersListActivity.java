package com.example.devandrin.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FollowersListActivity extends AppCompatActivity {
    private static FollowersListActivity instance;
    private static ArrayList<Profile> list ;
    private ProgressDialog pd;
    private static FollowerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setTitle(getIntent().getStringExtra("title"));
        list = new ArrayList<>();
        adapter = new FollowerAdapter(getApplicationContext(),list);
        ListView lv = (ListView) findViewById(R.id.lvContacts);
        lv.setAdapter(adapter);
        getTopArtists();
    }
    private void getTopArtists()
    {
        if(!getIntent().hasExtra("id"))
        {
            pd = new ProgressDialog(this);
            pd.setMessage("Loading Information ...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            String url = "https://www.eternalvibes.me/gettopartists/";
            JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0; i < response.length();i++)
                    {
                        try
                        {
                            JSONObject o = response.getJSONObject(i);
                            list.add(new Profile(o.getInt("id"),o.getString("username"),0));
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                    pd.cancel();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    pd.cancel();
                }
            });
            RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
        }
    }

    public static FollowersListActivity getInstance() {
        return instance;
    }
}
