package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    public static ArrayList<NewsFeedItem> arrTemp = new ArrayList<>();
    private static HomeActivity instance = null;
    private GoogleApiClient gac  = null;
    public static HomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (Utilities.isServicesEnabled(getApplicationContext()) == false) {

            Utilities.MakeSnack(findViewById(R.id.cLayout), "Unable to get Location");
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean LKey = false;
        LKey = sp.getBoolean(SettingsActivity.LOCATIONKEY,LKey);
        if(LKey)
        {
            sp = this.getSharedPreferences("Date",MODE_PRIVATE);
            Date date;
            GregorianCalendar gc = new GregorianCalendar();
            if(sp.contains("DatePosted"))
            {
                date = new Date(sp.getString("DatePosted",""));
                Date dateNow = gc.getTime();
                if(date.getDay()==dateNow.getDay() && date.getMonth()==dateNow.getMonth()&& date.getYear() == dateNow.getYear())
                {
                    long time =   dateNow.getTime()-date.getTime();
                    int Seconds = (int)time /1000;
                    if(Seconds >= 120)
                    {
                        date = postTime(sp);
                    }
                }
                else
                {
                    date = postTime(sp);
                }

            }
            else
            {
                date = postTime(sp);
            }
            Utilities.MakeToast(this,date.toString());
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpager);
        TabAdapter ta = new TabAdapter(getSupportFragmentManager(), HomeActivity.this);
        viewPager.setAdapter(ta);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        gac = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(LocationServices.API)
                .build();

    }
    private Date postTime(SharedPreferences sp)
    {
        GregorianCalendar gc = new GregorianCalendar();
        Date date= gc.getTime();
        SharedPreferences.Editor e = sp.edit();
        e.putString("DatePosted",date.toString());
        e.commit();
        return date;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            SharedPreferences sp = this.getSharedPreferences("userInfo",MODE_PRIVATE);
            String userID = "";
            String alias = "";
            userID = sp.getString("userID",userID);
            alias = sp.getString("alias",alias);
            Intent i = new Intent(this,ProfileActivity.class);
            i.putExtra("id",userID);
            i.putExtra("name",alias);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.remove("userID");
            e.remove("alias");
            e.commit();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
