package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendInviteActivity extends AppCompatActivity {
    private static ArrayList<Profile> list = new ArrayList<>();
    private static InviteAdapter iv ;
    private ProgressBar load = null;
    private static SendInviteActivity instance;
    private static final String url = "https://eternalvibes.me/getfollowing/";
    private static final String url2 = "https://www.eternalvibes.me/sendinvite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invite);
        instance = this;
        load = (ProgressBar) findViewById(R.id.siPBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_send_invite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lv = (ListView) findViewById(R.id.lvContacts);

        iv = new InviteAdapter(this,list);
        lv.setAdapter(iv);
        getContacts();
    }
    public static void sendAllInvites(final int eventID)
    {
        SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
        final String userID = sp.getString("userID", "");
        final String Alias = sp.getString("alias", "");
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url + userID, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    JSONObject o;
                    for(int i =0; i< response.length(); i++)
                    {
                        o = response.getJSONObject(i);
                        send(eventID,o.getInt("liked_id"),Integer.parseInt(userID),"You have been invited to attend an Event by "+Alias);
                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
    public static void send(int eventID, int receiver, int sender,String Message)
    {
        JSONObject o = new JSONObject();
        try
        {
            o.put("sender_user_id",sender);
            o.put("receiver_user_id",receiver);
            o.put("event_id",eventID);
            o.put("message",Message);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url2, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jor);
    }
    private  void  getContacts()
    {
        enableProgressBar();
        SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userID = "";
        userID = sp.getString("userID", userID);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url + userID, null, contactsResponse(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableProgressBar();
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
    private  Response.Listener<JSONArray> contactsResponse()
    {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                disableProgressBar();
                try
                {
                    JSONObject o;

                    for(int i =0; i< response.length(); i++)
                    {
                        o = response.getJSONObject(i);
                        list.add(new Profile(o.getInt("liked_id"),o.getString("username"),0));
                    }
                    if(iv != null)
                        iv.notifyDataSetChanged();
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static SendInviteActivity getInstance() {
        return instance;
    }
    public void enableProgressBar()
    {
        if (load.getVisibility() == View.GONE) {
            load.setVisibility(View.VISIBLE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.cLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(false);
                }
            }
        }
    }
    public void disableProgressBar()
    {
        if (load.getVisibility() == View.VISIBLE) {
            load.setVisibility(View.GONE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.cLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(true);
                }
            }
        }
    }
}
