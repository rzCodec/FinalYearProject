package com.example.devandrin.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ronald on 8/16/2017.
 */

public class RadarProfileUtil extends Content {

    private RadarProfileActivity radarProfileActivity = RadarProfileActivity.getInstance();
    /**
     * @param inflater  - Inflates and creates the required xml files
     * @param container - Holds a bunch of views together
     */
    public RadarProfileUtil(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public View displayContent(){
        View view = inflater.inflate(R.layout.view_radar_profile, container, false);
        displayProfileDetails(radarProfileActivity.getRCObj(), view);
        return view;
    }

    private void displayProfileDetails(RadarContent rcObj, View view){
        Button btnFollow = (Button) view.findViewById(R.id.btnRadarFollow);
        Button btnInviteToEvent = (Button) view.findViewById(R.id.btnRadarInviteToEvent);

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnInviteToEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

                TextView tvUsername = (TextView) view.findViewById(R.id.txtRadarProfileNameAndSurname);
        TextView tvDescription =(TextView) view.findViewById(R.id.txtRadarProfileDescription);
        TextView tvSkillset = (TextView) view.findViewById(R.id.txtRadarProfileSkillset);
        TextView tvEmail = (TextView) view.findViewById(R.id.txtRadarProfileEmailAddress);

        tvUsername.setTextSize(18);
        tvUsername.setTextColor(Color.BLACK);
        tvUsername.setText("Name : " + rcObj.getsUsername() + " " + rcObj.getsLastName());
        tvDescription.setText("Description : I'm just putting in this very long description for some testing purposes because I want to see what kind of effect it has on the other components \n" + rcObj.getDescription());
        tvSkillset.setText("Muscian Skillset: " + rcObj.getSkillset());
        tvEmail.setText("Email Address: " + rcObj.getsEmail());
    }

    @Override
    protected void update() {

    }

}
