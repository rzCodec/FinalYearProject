package com.example.devandrin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;


public class EventActivity extends AppCompatActivity {

    private static EventActivity instance;
    private ProgressBar load;
    public static EventActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        instance = this;
        load = (ProgressBar) findViewById(R.id.pbEventLoad) ;
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

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = sp.getString("userID", "");
        PersonalEventUtil.makeRequest(Integer.valueOf(id));
        EventUtil.makeRequest(Integer.valueOf(id));
    }
    public void enableProgressBar()
    {
        if (load.getVisibility() == View.GONE) {
            load.setVisibility(View.VISIBLE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.eLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(false);
                }
            }
        }
    }
    public void disableProgressBar()
    {
        if (load.getVisibility() == View.VISIBLE) {
            load.setVisibility(View.GONE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.eLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(true);
                }
            }
        }
    }
}
