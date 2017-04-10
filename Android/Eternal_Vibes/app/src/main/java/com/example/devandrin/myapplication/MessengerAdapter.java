package com.example.devandrin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(),c.getName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),c.getName(),Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }
}
