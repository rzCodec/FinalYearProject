package com.example.devandrin.myapplication;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Devandrin on 2017/03/15.
 */

class Utilities
{
    public static void MakeToast(Context context, String s)
    {
        Toast t= Toast.makeText(context,s,Toast.LENGTH_SHORT);
        t.show();
    }
}
