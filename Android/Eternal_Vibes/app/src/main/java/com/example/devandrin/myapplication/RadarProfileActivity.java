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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    //Will refactor this class out after Alpha, everything is good for now
    private class RadarProfile{
        private String sUsername;
        private String sLastName;
        private int iRating;
        private String sRank;
        private String sLocation;
        private int iDistance;
        private String sEmail;

        public RadarProfile(){

        }

        public String getsUsername() {
            return sUsername;
        }

        public void setsUsername(String sUsername) {
            this.sUsername = sUsername;
        }

        public String getsLastName() {
            return sLastName;
        }

        public void setsLastName(String sLastName) {
            this.sLastName = sLastName;
        }

        public int getiRating() {
            return iRating;
        }

        public void setiRating(int iRating) {
            this.iRating = iRating;
        }

        public String getsRank() {
            return sRank;
        }

        public void setsRank(String sRank) {
            this.sRank = sRank;
        }

        public String getsLocation() {
            return sLocation;
        }

        public void setsLocation(String sLocation) {
            this.sLocation = sLocation;
        }

        public int getiDistance() {
            return iDistance;
        }

        public void setiDistance(int iDistance) {
            this.iDistance = iDistance;
        }

        public String getsEmail() {
            return sEmail;
        }

        public void setsEmail(String sEmail) {
            this.sEmail = sEmail;
        }
    }

    //Attributes
    private RadarProfile rpObj = new RadarProfile();
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
        RadarProfile rpObj = new RadarProfile();
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
        TextView tvUsername = (TextView) findViewById(R.id.txtRadarProfileName);
        TextView tvLastName = (TextView) findViewById(R.id.txtRadarProfileLastName);
        TextView tvRank = (TextView) findViewById(R.id.txtRadarProfileRank);
        TextView tvRating = (TextView) findViewById(R.id.txtRadarProfileRating);
        TextView tvSkillset = (TextView) findViewById(R.id.txtRadarProfileSkillset);
        TextView tvDistanceAndLocation = (TextView) findViewById(R.id.txtRadarProfileDistanceLocation);
        TextView tvEmail = (TextView) findViewById(R.id.txtRadarProfileEmailAddress);

        tvUsername.setText("First Name: " + rpObj.getsUsername());
        tvLastName.setText("Last Name: " + rpObj.getsLastName());
        tvRank.setText("Rank: " + rpObj.getsRank());
        tvRating.setText("Rating: " + rpObj.getiRating());
        tvSkillset.setText("Muscian Skillset:");
        tvDistanceAndLocation.setText("Distance " + rpObj.getiDistance() + " km away and is in " + rpObj.getsLocation());
        tvEmail.setText("Email Address: " + rpObj.getsEmail());

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

    private void Initialize() {
        Intent i = getIntent();
        //sProfileName = i.getStringExtra("Name");
        //setTitle(i.getStringExtra("Name"));

        //Get the details from HomeActivity, so the text views can show data
        rpObj.setsUsername(i.getStringExtra("Name"));
        rpObj.setsLastName(i.getStringExtra("LastName"));
        rpObj.setsRank(i.getStringExtra("Rank"));
        rpObj.setiRating(i.getIntExtra("Rating", 0));
        rpObj.setiDistance(i.getIntExtra("Distance", 0));
        rpObj.setsLocation(i.getStringExtra("Location"));
        rpObj.setsEmail(i.getStringExtra("Email"));
    }

}
