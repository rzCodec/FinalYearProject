package com.example.devandrin.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.ref.ReferenceQueue;

/**
 * Created by Devandrin on 2017/03/18.
 */

public class RequestQueueSingleton
{
    private static RequestQueueSingleton instance;
    private RequestQueue rq;
    private static Context cont;

    private RequestQueueSingleton(Context c)
    {
        cont=c;
        rq=getRequestQueue();
    }
    public RequestQueue getRequestQueue()
    {
        if(rq==null)
        {
            rq= Volley.newRequestQueue(cont.getApplicationContext());
        }
        return rq;
    }
    public <T> void addToQ(Request<T> r)
    {
        getRequestQueue().add(r);
    }
    public static synchronized RequestQueueSingleton getInstance(Context c)
    {
        if(instance== null)
        {
            instance = new RequestQueueSingleton(c);
        }
        return instance;
    }

}
