package com.example.devandrin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    private Button loginb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loginb = (Button)findViewById(R.id.btnStart);
        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go();
            }
        });
    }
    private void Go()
    {
        Toast t = Toast.makeText(getApplicationContext(),"Woop Woop!",Toast.LENGTH_LONG);
        t.show();
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);

    }
}
