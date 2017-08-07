package com.example.devandrin.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.Spinner;
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
import java.util.StringTokenizer;

public class CreateEventActivity extends AppCompatActivity {

    private int year, month, day, hour, minute;
    private TextView date, time;
    private String Title, Description;
    private int Duration = 15;
    private Spinner duration;
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
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        instance = this;
        date = (TextView) findViewById(R.id.txtDate);
        time = (TextView) findViewById(R.id.txtTime);
        duration = (Spinner) findViewById(R.id.sdurtation);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        date.setText(year + "/" + month + "/" + day);
        time.setText(hour + "H" + minute);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.durations, android.R.layout.simple_spinner_dropdown_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        duration.setAdapter(adapter);
        duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Selection = parent.getItemAtPosition(position).toString();
                StringTokenizer st = new StringTokenizer(Selection);
                int Time = Integer.parseInt(st.nextToken());
                switch (Time)
                {
                    case 15:
                        Duration = 15;
                        break;
                    case 30:
                        Duration = 30;
                        break;
                    case 1:
                        Duration = 60;
                        break;
                    case 2:
                        Duration = 60*2;
                        break;
                    case 4:
                        Duration = 60*4;
                        break;
                    case 8:
                        Duration = 60*8;
                        break;
                    case 16:
                        Duration = 60*16;
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });
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
            StartProgressDialog();
            JSONObject o = createJson();
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pd.cancel();
                    instance.onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.cancel();
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
            o.put("duration",Duration);
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
    private void StartProgressDialog()
    {
        pd = new ProgressDialog(instance);
        pd.setMessage("Creating event");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        pd.show();
    }
}
