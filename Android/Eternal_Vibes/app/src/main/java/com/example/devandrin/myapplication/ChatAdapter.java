package com.example.devandrin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class ChatAdapter extends ArrayAdapter<MessageContent> {
    public ChatAdapter(Context context, ArrayList<MessageContent> msgList) {
        super(context, R.layout.chat_item, msgList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MessageContent msgObj = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
        }

        //Find the textviews and set each textview's content with the information from the arrayList
        TextView txtViewYourMsg = (TextView) convertView.findViewById(R.id.txtYourMsg);
        txtViewYourMsg.setText(msgObj.getsYourMessage());

        TextView txtViewReplyMsg = (TextView) convertView.findViewById(R.id.txtReplyMsg);
        txtViewReplyMsg.setText(msgObj.getsReplyMessage());

        return convertView;
    }
}
