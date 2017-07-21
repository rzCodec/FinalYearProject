package com.example.devandrin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class NewChatActivity extends AppCompatActivity {
    private ProgressBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        bar = (ProgressBar) findViewById(R.id.ncPBar);
        bar.setVisibility(View.GONE);
        ArrayList<Profile> list = new ArrayList<>();
        list.add(new Profile(1,"Devandrin",1));
        list.add(new Profile(2,"Joe",0));
        list.add(new Profile(3,"Lydia",2));
        list.add(new Profile(7,"Kyle",2));
        ListView lv = (ListView) findViewById(R.id.lvContacts);
        lv.setAdapter(new ContactChatAdapter(getApplicationContext(),list));
    }
}
