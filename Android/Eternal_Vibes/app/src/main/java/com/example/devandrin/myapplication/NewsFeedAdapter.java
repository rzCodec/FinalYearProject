package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Devandrin on 2017/04/11.
 */

public class NewsFeedAdapter extends ArrayAdapter<NewsFeedItem> {

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yy");

    public NewsFeedAdapter(Context context, ArrayList<NewsFeedItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewsFeedItem item = getItem(position);//Get Data To display
        viewComponents nfi;
        if (convertView == null) {
            nfi = new viewComponents();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_feed_item, parent, false);
            nfi.name = (TextView) convertView.findViewById(R.id.nfi_name);
            nfi.status = (TextView) convertView.findViewById(R.id.nfi_status);
            nfi.timestamp = (TextView) convertView.findViewById(R.id.nfi_timestamp);
            nfi.likes = (TextView) convertView.findViewById(R.id.nfi_likes);
            nfi.flags = (TextView) convertView.findViewById(R.id.nfi_flags);
            nfi.flag = (ImageButton) convertView.findViewById(R.id.nfi_btnFlag);
            nfi.like = (ImageButton) convertView.findViewById(R.id.nfi_btnLike);
            convertView.setTag(nfi);
        } else {
            nfi = (viewComponents) convertView.getTag();
        }
        nfi.name.setText("" + item.getUserID());
        nfi.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.getInstance(), ProfileActivity.class);
                i.putExtra("id", "" + item.getStatusID());
                i.putExtra("name", "" + item.getUserID());
                HomeActivity.getInstance().startActivity(i);
            }
        });
        nfi.status.setText(item.getStatus());
        cal.setTimeInMillis(item.getTimestamp());
        nfi.timestamp.setText(sdf.format(cal.getTime()));
        nfi.likes.setText(item.getLikes() + "");
        nfi.flags.setText(item.getFlags() + "");
        nfi.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.eternalvibes.me/likestatus/"+item.getStatusID();
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HomeActivity.getInstance().onResume();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utilities.MakeToast(HomeActivity.getInstance().getApplicationContext(), "Error");
                    }
                });
                RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(jor);
            }
        });
        nfi.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.eternalvibes.me/flagstatus/"+item.getStatusID();
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HomeActivity.getInstance().onResume();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utilities.MakeToast(HomeActivity.getInstance().getApplicationContext(), "Error");
                    }
                });
                RequestQueueSingleton.getInstance(HomeActivity.getInstance()).getRequestQueue().add(jor);
            }
        });
        return convertView;
    }

    private static class viewComponents {
        TextView name;
        TextView status;
        TextView timestamp;
        TextView likes;
        TextView flags;
        ImageButton like;
        ImageButton flag;
    }
}
