package com.example.devandrin.myapplication;

import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ronald on 8/3/2017.
 */

public class ChatThread extends Thread {

    private String userMessage = "";
    private int chatID;
    private int currentUserID;

    public ChatThread(){

    }

    public ChatThread(String userMessage, int chatID, int currentUserID){
        this.userMessage = userMessage;
        this.chatID = chatID;
        this.currentUserID = currentUserID;
    }

    @Override
    public void run(){
        //Making the chat request here
        String url = "https://eternalvibes.me/" + userMessage + "/" + chatID + "/" + currentUserID;

        Map<String, String> data = new HashMap<>();
        data.put("message", userMessage);
        data.put("chatID", Integer.toString(chatID));
        data.put("currentUserID", Integer.toString(chatID));

        final JSONObject jo = new JSONObject(data);

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "Error from send message request is : " + error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jo.toString() == null ? null : jo.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jo.toString(), "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = response.statusCode+"";
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }


        };

        RequestQueueSingleton.getInstance(HomeActivity.getInstance().getApplicationContext()).addToQ(sr);
    }//end run method
}//end class
