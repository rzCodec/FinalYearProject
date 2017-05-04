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

import org.json.JSONArray;
import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("name"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        String url = "https://www.eternalvibes.me/getuserinfo/" + getIntent().getStringExtra("id");
        Response.Listener listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Profile p = Profile.fromJSONArray(response).get(0);
                    TextView values = (TextView) findViewById(R.id.firstName);
                    values.setText(p.getFirstname());
                    values = (TextView) findViewById(R.id.lastName);
                    values.setText(p.getSurname());
                    values = (TextView) findViewById(R.id.email);
                    values.setText(p.getEmail());
                    values = (TextView) findViewById(R.id.values);
                    values.setText(p.toString());
                    values = (TextView) findViewById(R.id.SongLink);
                    values.setText(p.getSong_link());
                } catch (JSONException e) {
                    TextView values = (TextView) findViewById(R.id.values);
                    values.setText("Oops, something went wrong...");
                }
            }
        };
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView values = (TextView) findViewById(R.id.values);
                values.setText("Eish...");
            }
        };
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, url, null, listener, err);
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(sr);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
