package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    private String sUsername;
    private String sLastName;
    private int iRating;
    private String sRank;
    private String sLocation;
    private int iDistance;
    private String sEmail;
    private String Skillset;
    private String Description;

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
        setupRadarProfileDetails();


        //Experimental code below, please don't remove
        //createAlertBuilder();
        /*btnFollow = (FloatingActionButton) findViewById(R.id.btnFollowMusician);
        btnFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        */
    }

    //Method to setup text views and show the selected profile's details from the listview in RadarUtil
    private void setupRadarProfileDetails(){
        TextView tvUsername = (TextView) findViewById(R.id.txtRadarProfileNameAndSurname);
        TextView tvDescription =(TextView) findViewById(R.id.txtRadarProfileDescription);
        TextView tvSkillset = (TextView) findViewById(R.id.txtRadarProfileSkillset);
        TextView tvEmail = (TextView) findViewById(R.id.txtRadarProfileEmailAddress);

        tvUsername.setText("Name : " + sUsername + " " + sLastName + " is " + iDistance + "km away from you.");
        tvDescription.setText("Description : I'm just putting in this very long description for some testing purposes because I want to see what kind of effect it has on the other components" + Description);
        tvSkillset.setText("Muscian Skillset: " + Skillset);
        tvEmail.setText("Email Address: " + sEmail);
    }


    /* Leave this here for now, we can use it for Beta
       Shows the user Yes and No options in a dialog box
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
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Get the details from HomeActivity, so the text views can show data
    private void Initialize() {
        Intent i = getIntent();
        setTitle(i.getStringExtra("Alias") + "'s Profile");
        sUsername = i.getStringExtra("Name");
        sLastName = i.getStringExtra("LastName");
        iDistance = i.getIntExtra("Distance", 0);
        sLocation = i.getStringExtra("Location");
        sEmail = i.getStringExtra("Email");
        Skillset = i.getStringExtra("Skillset");
        Description = i.getStringExtra("Description");
    }

}
