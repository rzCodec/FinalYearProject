package com.example.devandrin.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
public class CreateEventActivity extends AppCompatActivity {

    private int year, month, dayofMonth;
    private TextView date = null;
    private void setYear(int year) {
        this.year = year;
    }

    private void setDayofMonth(int dayofMonth) {
        this.dayofMonth = dayofMonth;
    }

    private void setMonth(int month) {
        this.month = month;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        date = (TextView) findViewById(R.id.txtDate);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        dayofMonth = c.get(Calendar.DAY_OF_MONTH);
        date.setText(year+"/"+month+"/"+dayofMonth);
    }
    private DatePickerDialog.OnDateSetListener DateSetListener()
    {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setYear(year);
                setMonth(month);
                setDayofMonth(dayOfMonth);
                date.setText(year+"/"+month+"/"+dayOfMonth);

            }
        };
    }
    public void setDate(View v)
    {
        showDialog(3521);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 3521)
        {
            return new DatePickerDialog(this,DateSetListener(),year,month,dayofMonth);
        }
        return super.onCreateDialog(id);
    }

}
