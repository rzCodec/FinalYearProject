package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {
    public static int USER_ID = 0; //A user id that will checked and used in Radar and Messenger classes
    private ProgressBar load = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        load = (ProgressBar) findViewById(R.id.pbloadProfile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("name"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (getIntent().hasExtra("IsOwner")) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        String url = "https://www.eternalvibes.me/getuserinfo/" + getIntent().getStringExtra("id");
        Response.Listener listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Profile p = Profile.fromJSONArray(response).get(0);

                    USER_ID = p.getId(); //This user id will be used for the messenger and also for the radar
                    TextView values = (TextView) findViewById(R.id.firstName);
                    values.setText(p.getFirstname());
                    values = (TextView) findViewById(R.id.lastName);
                    values.setText(p.getSurname());
                    values = (TextView) findViewById(R.id.email);
                    values.setText(p.getEmail());
                    values = (TextView) findViewById(R.id.SongLink);
                    values.setText(p.getSong_link());
                    if (load.getVisibility() == View.VISIBLE) {
                        load.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    System.err.println("Oops, something went wrong...");
                }
            }
        };
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("Eish...");
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
