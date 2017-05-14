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
     ChatAdapter(Context context, ArrayList<MessageContent> msgList)
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item,parent,false);
            cmComponent.txtChatMsg = (TextView) convertView.findViewById(R.id.txtYourMsg);
            cmComponent.txtChatMsgReply = (TextView) convertView.findViewById(R.id.txtReplyMsg);
            convertView.setTag(cmComponent);
        }
        else
        {
            //Recycle the text view if it already exists to prevent memory overflow exceptions
            cmComponent = (chatMessageComponent) convertView.getTag();
        }

        try
        {
            cmComponent.txtChatMsg.setText(msgObj.getsMessage());
            if(msgObj.getMessageMine() )
            {
                cmComponent.txtChatMsg.setText(msgObj.getsMessage());
                cmComponent.txtChatMsg.setVisibility(View.VISIBLE);
                cmComponent.txtChatMsgReply.setVisibility(View.GONE);
            }
            else
            {
                cmComponent.txtChatMsgReply.setText(msgObj.getsMessage());
                cmComponent.txtChatMsg.setVisibility(View.GONE);
                cmComponent.txtChatMsgReply.setVisibility(View.VISIBLE);
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
        TextView txtChatMsgReply;
    }

} // end of class
