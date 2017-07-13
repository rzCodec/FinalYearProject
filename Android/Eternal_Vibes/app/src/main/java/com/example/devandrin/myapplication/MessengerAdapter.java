package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Devandrin on 2017/04/09.
 */

public class MessengerAdapter extends ArrayAdapter<Chat> {

    public MessengerAdapter(Context context, ArrayList<Chat> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Chat c = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messenger_item, parent, false);
        }
        SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String tempS = sp.getString("userID", "-1");
        int userID = Integer.parseInt(tempS);
        TextView temp = (TextView) convertView.findViewById(R.id.txtName);
        String alias="";
        if(userID != c.user1)
        {
            alias = HomeActivity.getDbHelper().getAlias(c.user1);
            temp.setText(alias);
            temp = (TextView) convertView.findViewById(R.id.txtLastMessage);
            temp.setText("User 2 ID: " +c.user2);
        }
        else if(userID != c.user2)
        {
            alias = HomeActivity.getDbHelper().getAlias(c.user2);
            temp.setText(alias);
            temp = (TextView) convertView.findViewById(R.id.txtLastMessage);
            temp.setText("User 2 ID: " +c.user1);
        }
        temp = (TextView) convertView.findViewById(R.id.txtTimestamp);
        temp.setText("Chat ID: " +c.ChatID);
        convertView.setOnClickListener(onChatClick(alias));
        return convertView;
    }
    private View.OnClickListener onChatClick(final String name)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.getInstance(), ChatActivity.class);
                i.putExtra("Name", name);
                HomeActivity.getInstance().startActivity(i);
            }
        };
    }

}
