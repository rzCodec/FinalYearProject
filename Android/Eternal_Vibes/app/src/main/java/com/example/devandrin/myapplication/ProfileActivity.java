package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Devandrin Kuni");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView t = (TextView) findViewById(R.id.firstName);
        t.setText("Devandrin");
        t = (TextView) findViewById(R.id.lastName);
        t.setText("Kuni");
        t = (TextView) findViewById(R.id.email);
        t.setText("201320596@student.uj.ac.za");
        String url = "https://www.eternalvibes.me/getuserinfo/1";
        Response.Listener listener =new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    Profile p = Profile.fromJsonArray(response).get(0);
                    TextView values = (TextView) findViewById(R.id.values);
                    values.setText(p.toString());
                }
                catch(JSONException e)
                {
                    TextView values = (TextView) findViewById(R.id.values);
                    values.setText("Never Work");
                }
            }
        };
        Response.ErrorListener err =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView values = (TextView) findViewById(R.id.values);
                values.setText("Eish...");
            }
        };
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, url, null,listener, err);
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(sr);
        

    }



}
