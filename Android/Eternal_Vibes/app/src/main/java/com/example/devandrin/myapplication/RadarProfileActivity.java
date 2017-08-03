package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    //Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_radar_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Initialize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void Initialize() {
        Intent i = getIntent();
        setTitle(i.getStringExtra("Name"));
    }

}
