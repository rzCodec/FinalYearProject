package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    private Button loginb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        PreferenceManager.setDefaultValues(this, R.xml.pref_location_services, true);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);
        if (seenPage()) {
            nextActivity();
        } else {
            loginb = (Button) findViewById(R.id.btnStart);
            loginb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity();
                }
            });
        }

    }

    private void nextActivity() {
        if (!seenPage()) {
            SharedPreferences sp = this.getSharedPreferences(getString(R.string.seenDash), Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean(getString(R.string.seenDash), true);
            e.commit();
        }
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private boolean seenPage() {
        SharedPreferences sp = this.getSharedPreferences(getString(R.string.seenDash), Context.MODE_PRIVATE);
        return sp.getBoolean(getString(R.string.seenDash), false);
    }
}
