package com.example.devandrin.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class ChatAdapter extends ArrayAdapter<MessageContent>
{
    public ChatAdapter(Context context, ArrayList<MessageContent> msgList)
    {
        super(context, R.layout.chat_item, msgList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        final MessageContent msgObj = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_message_item,parent,false);
        }

        //ConstraintLayout cl = (ConstraintLayout) convertView.findViewById(R.id.chatMsg_Layout);
        //LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.chatMessages_layout);


        try {
            TextView txtChatMsg = (TextView) convertView.findViewById(R.id.txtChatMessage);
            txtChatMsg.setText(msgObj.getsMessage());
            if(msgObj.getMessageMine() == true)
            {
                txtChatMsg.setTextColor(Color.BLACK);
                txtChatMsg.setGravity(Gravity.RIGHT);
            }
            else
            {
                txtChatMsg.setTextColor(Color.BLUE);
                txtChatMsg.setGravity(Gravity.LEFT);
            }
            return convertView;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }


        //Find the textviews and set each textview's content with the information from the arrayList
        /*
        TextView txtViewYourMsg = (TextView) convertView.findViewById(R.id.txtYourMsg);
        txtViewYourMsg.setText(msgObj.getsYourMessage());
        txtViewYourMsg.setGravity(Gravity.RIGHT);

        TextView txtViewReplyMsg = (TextView) convertView.findViewById(R.id.txtReplyMsg);
        txtViewReplyMsg.setText(msgObj.getsReplyMessage());
        txtViewYourMsg.setGravity(Gravity.LEFT);*/

        return convertView;
    }
}
