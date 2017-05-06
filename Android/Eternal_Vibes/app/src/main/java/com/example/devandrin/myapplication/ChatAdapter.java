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
        chatMessageComponent cmComponent;

        if(convertView == null)
        {
            //Make a new text view to show the messages
            cmComponent = new chatMessageComponent();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_message_item,parent,false);
            cmComponent.txtChatMsg = (TextView) convertView.findViewById(R.id.txtChatMessage);
            convertView.setTag(cmComponent);
        }
        else
        {
            //Recycle the text view if it already exists to prevent memory overflow exceptions
            cmComponent = (chatMessageComponent) convertView.getTag();
        }

        try
        {
            LinearLayout chatContainer = (LinearLayout) convertView.findViewById(R.id.chatContainer);
            cmComponent.txtChatMsg.setText(msgObj.getsMessage());
            if(msgObj.getMessageMine() == true)
            {
                cmComponent.txtChatMsg.setBackgroundResource(R.drawable.chat_msg_bubble);
                cmComponent.txtChatMsg.setTextColor(Color.WHITE);
                chatContainer.setGravity(Gravity.RIGHT);
            }
            else
            {
                cmComponent.txtChatMsg.setBackgroundResource(R.drawable.chat_msg_bubble_reply);
                cmComponent.txtChatMsg.setTextColor(Color.BLACK);
                chatContainer.setGravity(Gravity.LEFT);
            }
            return convertView;
        }
        catch(Exception e)
        {
            System.out.println("Error found : " + e.toString());
        }

        return convertView;
    } // end of function


    private static class chatMessageComponent
    {
        TextView txtChatMsg;
    }

} // end of class
