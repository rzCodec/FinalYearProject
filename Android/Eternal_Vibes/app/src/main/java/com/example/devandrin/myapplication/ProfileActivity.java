package com.example.devandrin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    public static int USER_ID = 0; //A user id that will checked and used in Radar and Messenger classes
    private static ProgressDialog pd = null;
    private FloatingActionButton fab;
    private ProfileActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance=this;
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Profile...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        setTitle(getIntent().getStringExtra("name"));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        String url = "https://www.eternalvibes.me/getuserinfo/" + getIntent().getStringExtra("id");
        Response.Listener listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    final Profile p = Profile.fromJSONArray(response).get(0);
                    USER_ID = p.getId(); //This user id will be used for the messenger and also for the radar
                    TextView values = (TextView) findViewById(R.id.firstName);
                    values.setText(p.getFirstname());
                    values = (TextView) findViewById(R.id.lastName);
                    values.setText(p.getSurname());
                    values = (TextView) findViewById(R.id.email);
                    values.setText(p.getEmail());
                    values = (TextView) findViewById(R.id.SongLink);
                    values.setText(p.getDescription());
                    if(!getIntent().hasExtra("IsOwner"))
                    {
                        fab.setOnClickListener(followUSer(p.getId()));
                        fab.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    System.err.println("Oops, something went wrong...");
                }
                finally {
                    pd.cancel();
                }

            }
        };
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
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
    private View.OnClickListener followUSer(final int ID)
    {
        SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String userID = sp.getString("userID", "");
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(instance);
                pd.setMessage("Following user...");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.show();
                String url = "https://eternalvibes.me/followuser/" + userID+"/"+ID;
                JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Utilities.MakeToast(HomeActivity.getInstance().getApplicationContext(), "User has been Followed");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Utilities.MakeToast(HomeActivity.getInstance().getApplicationContext(), "Error Following user");
                    }
                });
                RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jor);
            }
        };
    }
}
