package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Devandrin on 2017/04/09.
 */

public class MessengerAdapter extends ArrayAdapter<Chat>
{

    public MessengerAdapter(Context context, ArrayList<Chat> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        final Chat c = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messenger_item,parent,false);
        }
        TextView temp = (TextView) convertView.findViewById(R.id.txtName);
        temp.setText(c.getName());
        temp = (TextView) convertView.findViewById(R.id.txtLastMessage);
        temp.setText(c.getLastMessage());
        temp = (TextView) convertView.findViewById(R.id.txtTimestamp);
        temp.setText(c.getTimestamp()+"");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.getInstance(),ChatActivity.class);
                i.putExtra("Name",c.getName());
                HomeActivity.getInstance().startActivity(i);
            }
        });
        return convertView;
    }
}
