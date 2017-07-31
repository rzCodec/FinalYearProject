package com.example.devandrin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    private EditText et;
    private Button btnCancel, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        et = (EditText) findViewById(R.id.etPost);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().length() >= 10) {
                    makePost(et.getText().toString());
                    onBackPressed();
                } else {
                    Utilities.MakeToast(getApplicationContext(), "Post is too short");
                }
            }
        });
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        btnSend.performClick();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void makePost(String PostData) {
        Map<String, String> data = new HashMap<>();
        data.put("status", PostData);
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String url = "https://www.eternalvibes.me/setstatus/" + sp.getString("userID", "");
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Post", "onResponse: " + response.getString("affectedRows"));
                } catch (JSONException e) {
                    Log.d("PostException", "Exception thrown");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PostErr", "onErrorResponse: " + error.getMessage());
            }
        });
        RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(jor);
    }
}
