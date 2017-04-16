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

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/11.
 */

public class NewsFeedAdapter extends ArrayAdapter<NewsFeedItem> {
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
        nfi.name.setText(item.getUserID());
        nfi.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.getInstance(), ProfileActivity.class);
                HomeActivity.getInstance().startActivity(i);
            }
        });
        nfi.status.setText(item.getStatus());
        nfi.timestamp.setText(item.getTimestamp() + "");
        nfi.likes.setText(item.getLikes() + "");
        nfi.flags.setText(item.getFlags() + "");
        nfi.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.getInstance().getApplicationContext(), "Liked " + item.getUserID() + "'s post", Toast.LENGTH_SHORT).show();
            }
        });
        nfi.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.getInstance().getApplicationContext(), "Flagged " + item.getUserID() + "'s post", Toast.LENGTH_SHORT).show();
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
