package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    //Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_radar_profile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
