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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        tv.setText("Description: \n\n\n"+item.getInfo());
        tv = (TextView) findViewById(R.id.evInfo);
        tv.setText("Date: \n\n\n"+sdf.format(cal.getTime()));
        tv = (TextView) findViewById(R.id.evDate);
        cal.setTimeInMillis(item.getDate());
        String Duration;
        if(item.getStatus() > 30)
        {
            Duration = (item.getStatus()/60)+" Hours";
        }
        else
        {
            Duration = item.getStatus()+ " Minutes";
        }
        tv.setText("Duration: \n\n\n"+Duration);
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
                respondToInvite(1,getItem().getInvite().getId());
                return true;
            case R.id.not_attending:
                respondToInvite(3,getItem().getInvite().getId());
                return true;
            case R.id.maybe:
                respondToInvite(2,getItem().getInvite().getId());
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
        String url = "https://www.eternalvibes.me/cancelevent/" + getItem().getId();
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utilities.MakeToast(instance,"Event Cancelled");
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.MakeToast(instance,"Error Cancelling Event");
            }
        });
        RequestQueueSingleton.getInstance(this).addToQ(jor);
    }
    public void respondToInvite(int Response,int InviteID)
    {
        Map<String,Integer> m = new HashMap<>();
        m.put("response_id",Response);
        m.put("id",InviteID);
        JSONObject j = new JSONObject(m);
        String url = "https://www.eternalvibes.me/responsetoinvite/";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utilities.MakeToast(instance,"Response sent");
                instance.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.MakeToast(instance,"Unable to respond");
            }
        });
        RequestQueueSingleton.getInstance(this).addToQ(jor);
    }
}
