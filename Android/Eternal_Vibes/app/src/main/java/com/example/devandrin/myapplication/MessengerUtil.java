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

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Chat> clist = new ArrayList<>();
        clist.add(new Chat(11,"Han Solo","Where you at Bro??"));
        clist.add(new Chat(13,"NewTon","I hope you become a null"));
        clist.add(new Chat(14,"NewTon","I hope you become a null"));
        clist.add(new Chat(15,"NewTon","I hope you become a null"));
        clist.add(new Chat(16,"NewTon","I hope you become a null"));
        clist.add(new Chat(17,"NewTon","I hope you become a null"));
        clist.add(new Chat(18,"NewTon","I hope you become a null"));
        clist.add(new Chat(19,"NewTon","I hope you become a null"));
        MessengerAdapter ma = new MessengerAdapter(HomeActivity.getInstance().getApplicationContext(),clist);
        ListView l = (ListView) view.findViewById(R.id.MessengerList);
        l.setAdapter(ma);
        return view;
    }
}
