package com.example.devandrin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Devandrin on 2017/03/15.
 */

class Utilities {
    public static void MakeToast(Context context, String s) {
        Toast t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }

    public static boolean isServicesEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean GPS;
        try {
            GPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            GPS = false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean data = (info == null) ? false : true;
        if (data == false || GPS == false) {
            return false;
        } else {
            return true;
        }
    }

    public static void MakeSnack(View view, String m) {
        Snackbar s = Snackbar.make(view, m, Snackbar.LENGTH_LONG);
        s.setActionTextColor(Color.RED);
        s.show();
    }

    public boolean isLSEnabled(Activity a) {
        boolean answer = false;
        SharedPreferences sp = a.getSharedPreferences("pref_location_services", Context.MODE_PRIVATE);
        answer = sp.getBoolean(SettingsActivity.LOCATIONKEY, false);
        return answer;
    }


}
