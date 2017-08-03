package com.example.devandrin.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity {

    private int year, month, day, hour, minute;
    private TextView date, time;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        date = (TextView) findViewById(R.id.txtDate);
        time = (TextView) findViewById(R.id.txtTime);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        date.setText(year + "/" + month + "/" + day);
        time.setText(hour + "H" + minute);
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
}
