package com.example.devandrin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class EventActivity extends AppCompatActivity {

    private static EventActivity instance;

    public static EventActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        instance = this;
        ViewPager viewPager = (ViewPager) findViewById(R.id.eventPager);
        EventTabAdapter ta = new EventTabAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(ta);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.eventtab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void createEvent(View v) {
        startActivity(new Intent(this, CreateEventActivity.class));
    }


    /**
     * The following methods are used to setup a floating context menu.
     *
     * @param lv - View to be passed to the menu. The menu can also receive
     *               a listview
     */
    public void start_MenuActivity(ListView lv) {
        registerForContextMenu(lv);
        openContextMenu(lv);
        unregisterForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_menu, menu);
    }

    /*
     *
     * @param item - Each item is defined in the res/menu
     * @return - True or false if the item has been selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ReviewEvent:
                //Add custom functionality here
                Toast.makeText(this, "Hello Context Menu!",
                        Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
