package com.example.devandrin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch s =(Switch) findViewById(R.id.swtBroadcast);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Toast t = Toast.makeText(getApplicationContext(),getString(R.string.broadcastY),Toast.LENGTH_SHORT);
                    t.show();
                }
                else
                {
                    Toast t = Toast.makeText(getApplicationContext(),getString(R.string.broadcastN),Toast.LENGTH_SHORT);
                    t.show();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
