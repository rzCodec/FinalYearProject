package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventViewActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static EventItem item;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss \n dd/MM/yy");
    public static void setItem(EventItem item) {
        EventViewActivity.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        setTitle(item.getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab ;
        if(item.hasInvite())
        {
            fab= (FloatingActionButton) findViewById(R.id.fabManagement);
            fab.setVisibility(View.GONE);
        }
        else
        {
            fab= (FloatingActionButton) findViewById(R.id.fabResponse);
            fab.setVisibility(View.GONE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.evName);
        tv.setText("Event: \n\n"+item.getName());
        tv = (TextView) findViewById(R.id.evInfo);
        tv.setText("Description: \n\n"+item.getInfo());
        tv = (TextView) findViewById(R.id.evDate);
        cal.setTimeInMillis(item.getDate());
        tv.setText("Event Date: \n\n"+sdf.format(cal.getTime()));
    }
    public void EventManagement(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popupmanagement);
        popup.show();
    }

    public void EventResponseManagement(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popupresponse);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.attending:
                Toast.makeText(this,"Attending Event",Toast.LENGTH_LONG).show();
                onBackPressed();
                return true;
            case R.id.not_attending:
                Toast.makeText(this,"Not Attending",Toast.LENGTH_LONG).show();
                onBackPressed();
                return true;
            case R.id.maybe:
                Toast.makeText(this,"Maybe",Toast.LENGTH_LONG).show();
                onBackPressed();
                return true;
            case R.id.cancelevent:
                Toast.makeText(this,"Event Cancelled",Toast.LENGTH_LONG).show();
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventActivity.getInstance().onResume();
        finish();
    }
}
