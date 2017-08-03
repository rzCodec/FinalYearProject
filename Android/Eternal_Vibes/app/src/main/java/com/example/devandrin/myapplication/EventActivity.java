package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class EventActivity extends AppCompatActivity {

    private static EventActivity instance;

    public static EventActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        instance = this;
        ViewPager viewPager = (ViewPager) findViewById(R.id.eventPager);
        EventTabAdapter ta = new EventTabAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(ta);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.eventtab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void createEvent(View v) {
        startActivity(new Intent(this, CreateEventActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
