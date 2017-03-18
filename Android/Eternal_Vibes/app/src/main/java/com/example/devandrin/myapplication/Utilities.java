package com.example.devandrin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.view.View;
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
    public static boolean isServicesEnabled(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean Data=false;
        boolean GPS=false;

        try
        {
            Data = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            GPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch(Exception e){return false;}
        if((Data == true) && (GPS == true))
        {
            return true;
        }else
        {
            return false;
        }
    }
    public static void MakeSnack(View view, String m)
    {
        Snackbar s = Snackbar.make(view,m,Snackbar.LENGTH_LONG);
        s.setActionTextColor(Color.RED);
        s.show();
    }
    public boolean isLSEnabled(Activity a)
    {
        boolean answer = false;
        SharedPreferences sp = a.getSharedPreferences("pref_location_services",Context.MODE_PRIVATE);
        answer = sp.getBoolean(SettingsActivity.LOCATIONKEY,false);
        return answer;
    }


}
