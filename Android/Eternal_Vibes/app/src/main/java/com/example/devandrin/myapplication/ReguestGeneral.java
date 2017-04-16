package com.example.devandrin.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by Devandrin on 2017/03/31.
 */

public abstract class ReguestGeneral<T> extends Request<T> {
    private final String URL;
    private final Class<T> clazz;
    private final Response.Listener<T> listener;


    public ReguestGeneral(int method, String url, Response.ErrorListener listener, String URL, Class<T> clazz, Response.Listener<T> listener1) {
        super(method, url, listener);
        this.URL = URL;
        this.clazz = clazz;
        this.listener = listener1;
    }
}
