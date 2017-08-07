package com.example.devandrin.myapplication;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventViewActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static EventItem item;
    Calendar cal = Calendar.getInstance();
    private static EventViewActivity instance;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss \n dd/MM/yy");
    public static void setItem(EventItem item) {
        EventViewActivity.item = item;
    }

    public static EventItem getItem() {
        return item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if(item == null)
        {
            onBackPressed();
            return;
        }
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
            case R.id.send_invites :
                Intent i = new Intent(this,SendInviteActivity.class);
                i.putExtra("event_id",getItem().getId());
                startActivity(i);
                return true;
            case R.id.invite_all :
                SendInviteActivity.sendAllInvites(getItem().getId());
                Toast.makeText(this,"Invitations sent",Toast.LENGTH_LONG).show();
                return true;
            case R.id.cancelevent:
                CancelEvent();
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void CancelEvent()
    {
        String url = "https://www.eternalvibes.me/cancelevent" + getItem().getId();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utilities.MakeToast(instance,"Event Cancelled");
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.MakeToast(instance,"Error Cancelling Event");
            }
        });
        RequestQueueSingleton.getInstance(this).addToQ(sr);
    }
}
