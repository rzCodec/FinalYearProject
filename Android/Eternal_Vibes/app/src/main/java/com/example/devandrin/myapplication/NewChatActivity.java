package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewChatActivity extends AppCompatActivity {
    private ProgressBar bar = null;
    private ArrayList<Profile> list;
    private ContactChatAdapter cca;
    private static NewChatActivity instance = null;
    private final String url = "https://eternalvibes.me/getfollowing/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        instance = this;
        bar = (ProgressBar) findViewById(R.id.ncPBar);
        bar.setVisibility(View.GONE);
        list = new ArrayList<>();
        ListView lv = (ListView) findViewById(R.id.lvContacts);
        cca = new ContactChatAdapter(getApplicationContext(), list);
        lv.setAdapter(cca);
        getContacts(1);

    }

    public static NewChatActivity getInstance() {
        return instance;
    }

    private void getContacts(final int  Count)
    {
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String userID = "";
        userID = sp.getString("userID", userID);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url + userID, null, contactsResponse(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).addToQ(jar);
    }
    private Response.Listener<JSONArray> contactsResponse()
    {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    JSONObject o;

                    for(int i =0; i< response.length(); i++)
                    {
                        o = response.getJSONObject(i);
                        list.add(new Profile(o.getInt("user_id"),o.getString("username"),0));
                    }
                    cca.notifyDataSetChanged();
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
}
