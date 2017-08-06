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

    //I need the chatID to be passed into the onChatClick method to find which list of messages to get
    private Chat c = null;

    public MessengerAdapter(Context context, ArrayList<Chat> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final Chat c = getItem(position);
        c = getItem(position);
        viewComponents vc ;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messenger_item, parent, false);
            vc = new viewComponents();
            vc.Username = (TextView) convertView.findViewById(R.id.txtName);
            vc.Timestamp = (TextView) convertView.findViewById(R.id.txtTimestamp);
            vc.LastMessage = (TextView) convertView.findViewById(R.id.txtLastMessage);
            convertView.setTag(vc);
        }
        else
        {
            vc =(viewComponents) convertView.getTag();
        }
        SharedPreferences sp = HomeActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String tempS = sp.getString("userID", "-1");
        String alias ="";
        if (tempS.equals(String.valueOf(c.user1))) {
            alias = HomeActivity.getDbHelper().getAlias(c.user2);
            vc.Username.setText(alias);
            vc.LastMessage.setText("User 2 ID: " + c.user2);
        } else if (tempS.equals(String.valueOf(c.user2))) {
            alias = HomeActivity.getDbHelper().getAlias(c.user1);
            vc.Username.setText(alias);
            vc.LastMessage.setText("User 2 ID: " + c.user1);
        }
        vc.Timestamp.setText("Chat ID: " + c.ChatID);
        convertView.setOnClickListener(onChatClick(alias,c.ChatID));
        return convertView;
    }

    private View.OnClickListener onChatClick(final String name,final int ChatID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.getInstance(), ChatActivity.class);
                i.putExtra("Name", name);

                //i.putExtra("CurrentUserID", c.user1);
                //i.putExtra("ChatID", c.ChatID);

                i.putExtra("ChatID",ChatID);
                HomeActivity.getInstance().startActivity(i);
            }
        };
    }
    private static class viewComponents
    {
        TextView Timestamp,LastMessage,Username;
    }
}
