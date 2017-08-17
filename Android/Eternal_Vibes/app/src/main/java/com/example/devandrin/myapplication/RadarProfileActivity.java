package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.widget.TextView;

/**
 * Created by Ronald on 8/2/2017.
 */

public class RadarProfileActivity extends AppCompatActivity {

    //Attributes to set the information from a user's profile
    private String sUsername;
    private String sLastName;
    private String sAlias;
    private int iRating;
    private String sRank;
    private String sLocation;
    private int iDistance;
    private String sEmail;
    private String Skillset;
    private String Description;

    private static RadarProfileActivity instance;
    private iRadarResponseListener radarResponseListener;
    RadarContent radarContentObjToBePassed = new RadarContent();

    //Attributes
    private FloatingActionButton btnFollow;
    private String sProfileName = "";
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.radarProfileToolbar);
        setSupportActionBar(toolbar);
        instance = this;
        Initialize();

        ViewPager radarProfileViewPager = (ViewPager) findViewById(R.id.radarProfilePager);
        GeneralTabAdapter GTA = new GeneralTabAdapter(getSupportFragmentManager(), this, 2, sAlias + "'s Profile", sAlias + "'s Event List");
        GeneralPageFragment.sActivityType = "RadarProfileActivity";
        radarProfileViewPager.setAdapter(GTA);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.radarProfileTab);
        tabLayout.setupWithViewPager(radarProfileViewPager);

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


    public RadarContent getRCObj(){
        return radarContentObjToBePassed;
    }

    public static RadarProfileActivity getInstance(){
        return instance;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Get the details from HomeActivity, so the text views can show data
    private void Initialize() {
        Intent i = getIntent();
        setTitle("Eternal Vibes");

        int iUserID = i.getIntExtra("UserID", 0);
        sAlias = i.getStringExtra("Alias");
        sUsername = i.getStringExtra("Name");
        sLastName = i.getStringExtra("LastName");
        iDistance = i.getIntExtra("Distance", 0);
        sLocation = i.getStringExtra("Location");
        sEmail = i.getStringExtra("Email");
        Skillset = i.getStringExtra("Skillset");
        Description = i.getStringExtra("Description");

        radarContentObjToBePassed.setUserID(iUserID);
        radarContentObjToBePassed.setsAlias(sAlias);
        radarContentObjToBePassed.setsUsername(sUsername);
        radarContentObjToBePassed.setsLastName(sLastName);
        radarContentObjToBePassed.setDistance(iDistance);
        radarContentObjToBePassed.setsLocation(sLocation);
        radarContentObjToBePassed.setsEmail(sEmail);
        radarContentObjToBePassed.setSkillset(Skillset);
        radarContentObjToBePassed.setDescription(Description);
    }

}
