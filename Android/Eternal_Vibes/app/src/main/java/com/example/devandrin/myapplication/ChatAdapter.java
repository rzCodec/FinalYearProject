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
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronnie on 2017-04-24.
 */

public class ChatAdapter extends ArrayAdapter<MessageContent>
{
    ChatAdapter(Context context, ArrayList<MessageContent> msgList)
    {
        super(context, R.layout.chat_message_grid_item, msgList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_message_grid_item, parent, false);
            cmComponent.txtChatMsg = (TextView) convertView.findViewById((R.id.txtGridChatMessage));
            cmComponent.txtMsgChatDate = (TextView) convertView.findViewById((R.id.txtGridChatMessageDate));
            convertView.setTag(cmComponent);
        }
        else
        {
            cmComponent = (chatMessageComponent) convertView.getTag();
        }

        try
        {
            //Get the linear layout because its gravity will change.
            //The textview will move according to the linear layout's gravity.
            LinearLayout chatContainer = (LinearLayout) convertView.findViewById(R.id.chatMsgLinearLayout);
            LinearLayout chatMsgDateContainer = (LinearLayout) convertView.findViewById(R.id.chatMsgDateLinearLayout);


            //Simulate a time sent part at the end of each message for now...

            String sSpacing = "";
            for(int i = 0; i < msgObj.getsMessage().length() + 1; i++)
            {
                sSpacing += " ";
            }

            cmComponent.txtMsgChatDate.setText("DD/MM/YY");
            cmComponent.txtMsgChatDate.setTextColor(Color.BLUE);
            chatMsgDateContainer.setGravity(Gravity.CENTER_HORIZONTAL);
            //Set the message in the textview with time below it.
            //cmComponent.txtChatMsg.setText(msgObj.getsMessage() + "\n" + sSpacing + "19:45");
            String sDateAndTime = "5 April 2017 @ 20:21";
            cmComponent.txtChatMsg.setText(msgObj.getsMessage());
            //cmComponent.txtMsgChatDate.setText("MM/DD/YY");
            //cmComponent.txtMsgChatDate.setTextColor(Color.BLUE);
            //cmComponent.txtMsgChatDate.setGravity(Gravity.CENTER_HORIZONTAL);

            if(msgObj.getMessageMine())
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
        TextView txtMsgChatDate;
    }
} // end of class

