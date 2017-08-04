package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    //Attributes
    private FloatingActionButton btnFollow;
    private String sProfileName = "";
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profiletoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Initialize();
        createAlertBuilder();

        btnFollow = (FloatingActionButton) findViewById(R.id.btnFollowMusician);
        btnFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void createAlertBuilder(){
        builder = new AlertDialog.Builder(RadarProfileActivity.this);
        builder.setTitle("Would you like to follow " + sProfileName + "?");
        builder.setMessage("Users that follow each other can send messages to each other and view their posts and likes");

        //The Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"You are now following " + sProfileName,Toast.LENGTH_LONG).show();
                //Request to follow a user will be implemented here
            }
        });

        //The No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"You are now following " + sProfileName,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void Initialize() {
        Intent i = getIntent();
        sProfileName = i.getStringExtra("Name");
        setTitle(i.getStringExtra("Name"));
    }

}
