package com.example.devandrin.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Dashboard extends AppCompatActivity {
    private static final String AUTHENTICATION_SIGNITURE = "https://www.eternalvibes.me/SoundAuthen?";
    private  Dashboard c;
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(isSeen())
        {
            nextActivity();
        }
        else
        {
            c= this;
            PreferenceManager.setDefaultValues(this, R.xml.pref_location_services, true);
            PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);
            wv= (WebView)findViewById(R.id.soundAuthen);
            WebSettings settings = wv.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(true);
            wv.loadUrl(Api_Details.getSoundURL());
            wv.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if(url.contains(AUTHENTICATION_SIGNITURE))
                    {
                        nextActivity();
                    }
                    return false;
                }

            });
            wv.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if(newProgress >=80)
                    {
                        setTitle("Eternal Vibes");
                    }
                    else
                    {
                        c.setTitle("Loading...");
                    }
                }
            });
        }

    }

    private void nextActivity() {

        SharedPreferences sp = this.getSharedPreferences("userInfo",MODE_PRIVATE);
       SharedPreferences.Editor e = sp.edit();
        e.putString("userID", "3");
        e.putString("alias","Vanneh");
        e.commit();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean isSeen()
    {
        SharedPreferences sp = this.getSharedPreferences("userInfo",MODE_PRIVATE);
        return (sp.contains("userID"))? true: false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int result= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS)
        {
            Dialog err = GoogleApiAvailability.getInstance().getErrorDialog(this,result,0);
            err.show();
        }
    }


}
