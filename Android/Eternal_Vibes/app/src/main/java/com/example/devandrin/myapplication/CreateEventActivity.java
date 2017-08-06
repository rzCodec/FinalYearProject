package com.example.devandrin.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity {

    private int year, month, day, hour, minute;
    private TextView date, time;
    private String Title, Description;
    private Calendar c;
    private final static String url = "https://www.eternalvibes.me/createevent/";
    private void setYear(int year) {
        this.year = year;
    }

    private void setDay(int day) {
        this.day = day;
    }

    private void setMonth(int month) {
        this.month = month;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public static CreateEventActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        instance = this;
        date = (TextView) findViewById(R.id.txtDate);
        time = (TextView) findViewById(R.id.txtTime);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        date.setText(year + "/" + month + "/" + day);
        time.setText(hour + "H" + minute);
    }
    public  void CheckData(View v)
    {
            TextView tv = (TextView) findViewById(R.id.txtTitle);
            Title = tv.getText().toString();
            tv=(TextView) findViewById(R.id.txtDescription);
            Description = tv.getText().toString();
            if(Title.length() <= 5)
            {
                Utilities.MakeToast(instance,"Title Length is too short.");
                return;
            }
            if(Description.length() <= 10)
            {
                Utilities.MakeToast(instance,"Description needs to be longer.");
                return;
            }
            c.set(year,month,day,hour,minute,0);
            if(c.getTimeInMillis() <= System.currentTimeMillis())
            {
                Utilities.MakeToast(instance,"Date cannot be set before current time.");
                return;
            }
            JSONObject o = createJson();
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Utilities.MakeToast(instance,"Event Created");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utilities.MakeToast(instance,"Error creating event.");
                }
            });
            RequestQueueSingleton.getInstance(instance).addToQ(jor);
    }
    private JSONObject createJson()
    {
        JSONObject o = new JSONObject();
        try
        {
            o.put("title",Title);
            o.put("date",c.getTimeInMillis());
            o.put("description",Description);
            o.put("status",1);
            SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
            o.put("user_id",Integer.parseInt(sp.getString("userID","")));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return o;
    }
    private DatePickerDialog.OnDateSetListener DateSetListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setYear(year);
                setMonth(month);
                setDay(dayOfMonth);
                date.setText(year + "/" + month + "/" + dayOfMonth);

            }
        };
    }

    public void setDate(View v) {
        showDialog(3521);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 3521) {
            DatePickerDialog d = new DatePickerDialog(this, DateSetListener(), year, month, day);
            d.getDatePicker().setMinDate(System.currentTimeMillis());
            return d;
        } else if (id == 1253) {
            return new TimePickerDialog(this, TimeSetListener(), hour, minute, true);
        }
        return super.onCreateDialog(id);
    }

    private TimePickerDialog.OnTimeSetListener TimeSetListener() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setHour(hourOfDay);
                setMinute(minute);
                time.setText(hourOfDay + "H" + minute);
            }
        };
    }

    public void setTime(View v) {
        showDialog(1253);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
