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
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view;
        textView.setText("Messenger");
        return view;
    }
}
