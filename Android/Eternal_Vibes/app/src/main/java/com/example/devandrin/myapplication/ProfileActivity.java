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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

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
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*try
                {
                    JSONObject o = new JSONObject(response.substring(1,response.length()-1));
                    TextView values = (TextView) findViewById(R.id.values);
                    values.setText(o.toString());


                }
                catch(JSONException e)
                {
                    TextView values = (TextView) findViewById(R.id.values);
                    values.setText("Eish...");
                }*/
                Gson g = new Gson();
                Profile p = g.fromJson(response.substring(1, response.length() - 1), Profile.class);
                TextView values = (TextView) findViewById(R.id.values);
                values.setText(p.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView values = (TextView) findViewById(R.id.values);
                values.setText("Never Work");
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(sr);
    }

    public class Profile {
        int statusID;
        int userID;
        long timestamp;
        String status;
        String extraInfo;
        int flagged;
        int liked;

        public Profile(int statusID, int userID, long timestamp, String status, String extraInfo, int flagged, int liked) {
            this.statusID = statusID;
            this.userID = userID;
            this.timestamp = timestamp;
            this.status = status;
            this.extraInfo = extraInfo;
            this.flagged = flagged;
            this.liked = liked;
        }

        public int getStatusID() {
            return statusID;
        }

        public void setStatusID(int statusID) {
            this.statusID = statusID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(String extraInfo) {
            this.extraInfo = extraInfo;
        }

        public int getFlagged() {
            return flagged;
        }

        public void setFlagged(int flagged) {
            this.flagged = flagged;
        }

        public int getLiked() {
            return liked;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        @Override
        public String toString() {
            return "Details :\n"
                    + statusID + "\n"
                    + userID + "\n"
                    + timestamp + "\n"
                    + status + "\n"
                    + extraInfo + "\n"
                    + flagged + "\n"
                    + liked + "\n";
        }
    }

}
