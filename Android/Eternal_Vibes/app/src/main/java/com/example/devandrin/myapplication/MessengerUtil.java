package com.example.devandrin.myapplication;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Devandrin on 2017/04/01.
 */

public class MessengerUtil extends Content
{
    private final static String[] CONTENT ={"Joe", "Dv", "Nola", "Jimmy"};
    public MessengerUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent() {
        View view = inflater.inflate(R.layout.messenger_frag, container, false);
        TextView temp = (TextView) view.findViewById(R.id.m_profile_name);
        temp.setText("Han Solo");
        temp = (TextView) view.findViewById(R.id.m_message);
        temp.setText("Where you at Bro??");
        temp = (TextView) view.findViewById(R.id.m_profile_name1);
        temp.setText("NewTon");
        temp = (TextView) view.findViewById(R.id.m_message1);
        temp.setText("I hope you become a null");
        temp = (TextView) view.findViewById(R.id.m_profile_name2);
        temp.setText("DvdK01");
        temp = (TextView) view.findViewById(R.id.m_message2);
        temp.setText("Dude Wanna meet up? I got great idea for a new hit");
        temp = (TextView) view.findViewById(R.id.m_profile_name3);
        temp.setText("SUperNull");
        temp = (TextView) view.findViewById(R.id.m_message3);
        temp.setText("How now brown cow?");

        return view;
    }
}
