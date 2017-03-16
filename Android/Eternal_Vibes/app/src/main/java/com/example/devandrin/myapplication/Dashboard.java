package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        if (seenPage()) {
            nextActivity();
        } else {
            loginb = (Button) findViewById(R.id.btnStart);
            loginb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Go();
                }
            });
        }

    }

    private void Go() {
        nextActivity();
    }

    private void nextActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private boolean seenPage() {
        SharedPreferences sp = this.getSharedPreferences(getString(R.string.seenDash), Context.MODE_PRIVATE);
        boolean value = false;
        boolean isSeen = sp.getBoolean(getString(R.string.seenDash), value);
        return isSeen;
    }
}
