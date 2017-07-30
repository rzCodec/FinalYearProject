package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    static ProgressBar load = null;
    private static HomeActivity instance = null;
    private static DBHelper dbHelper = null;
    FloatingActionButton newChatFab, newPostFab, btn_sortRadar;

    private String SortRadarType;
    private Boolean isAscending;

    static HomeActivity getInstance() {
        return instance;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        load = (ProgressBar) findViewById(R.id.pbLoad);
        dbHelper = new DBHelper(this);
        instance = this;

        //Floating buttons to make a post or send a message
        newChatFab = (FloatingActionButton) findViewById(R.id.new_chat);
        newChatFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartNewChat();
            }
        });

        newPostFab = (FloatingActionButton) findViewById(R.id.new_post);
        newPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostActivity();
            }
        });

        btn_sortRadar = (FloatingActionButton) findViewById(R.id.sortRadar);
        btn_sortRadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_SortActivity(v);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (!Utilities.isServicesEnabled(getApplicationContext())) {

            Utilities.MakeSnack(findViewById(R.id.cLayout), "Location services disabled");
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpager);
        TabAdapter ta = new TabAdapter(getSupportFragmentManager(), HomeActivity.this);
        viewPager.setAdapter(ta);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    newChatFab.setVisibility(View.VISIBLE);
                    btn_sortRadar.setVisibility(View.GONE);
                    load.setVisibility(View.GONE);
                } else {
                    newChatFab.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {
                    newPostFab.setVisibility(View.VISIBLE);
                    btn_sortRadar.setVisibility(View.GONE);
                    onResume();
                } else {
                    newPostFab.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 2) {
                    newChatFab.setVisibility(View.GONE);
                    newPostFab.setVisibility(View.GONE);
                    btn_sortRadar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(1);

    }

    //Start a new chat activity for the messenger
    private void StartNewChat() {
        startActivity(new Intent(this, NewChatActivity.class));
    }

    /**
     * The following methods are used to setup a floating context menu.
     *
     * @param sender - View to be passed to the menu. The menu can also receive
     *               a listview
     */
    private void start_SortActivity(View sender) {
        registerForContextMenu(sender);
        openContextMenu(sender);
        unregisterForContextMenu(sender);
    }

    /**
     * Create the menu when the user selects the floating button
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_options_menu, menu);
    }

    /**
     * Sort the radar profiles based on the user's selection
     * The Profile Queue class is then used
     *
     * @param item - Each item is defined in the res/menu
     * @return - True or false if the item has been selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.NearestDistance:
                RadarUtil.UpdatedSort_RadarProfiles("DISTANCE", true);
                return true;

            case R.id.FurthestDistance:
                RadarUtil.UpdatedSort_RadarProfiles("DISTANCE", false);
                return true;

            case R.id.HighestRating:
                RadarUtil.UpdatedSort_RadarProfiles("RATING", false);
                return true;

            case R.id.LowestRating:
                RadarUtil.UpdatedSort_RadarProfiles("RATING", true);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    private void UpdateLocation() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean(SettingsActivity.LOCATIONKEY, false)) {
            sp = this.getSharedPreferences("Date", MODE_PRIVATE);
            Date date;
            GregorianCalendar gc = new GregorianCalendar();
            date = gc.getTime();
            if (sp.contains("DatePosted")) {
                gc.setTimeInMillis(Long.parseLong(sp.getString("DatePosted", "")));
                Date dateNow = gc.getTime();
                if (date.getDay() == dateNow.getDay() && date.getMonth() == dateNow.getMonth() && date.getYear() == dateNow.getYear()) {
                    long time = dateNow.getTime() - date.getTime();
                    int Seconds = (int) time / 1000;
                    if (Seconds <= 1200) {
                        return;
                    }
                }
            }
        }
        postTime(sp);
    }

    private void postTime(final SharedPreferences sp) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean GPS;
        try {
            GPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            GPS = false;
        }
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean data = info != null;
        if (!data || !GPS) {
            Utilities.MakeSnack(findViewById(R.id.cLayout), "Unable to get location");
        } else {
            try {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1200, 50, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
                Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (l != null) {
                    SharedPreferences sharedP = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String url = "https://www.eternalvibes.me/setuserlocation/" + sharedP.getString("userID", "") +
                            "/" + l.getLatitude() +
                            "/" + l.getLongitude();

                    JsonObjectRequest jar = new JsonObjectRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GregorianCalendar gc = new GregorianCalendar();
                            long date = gc.getTimeInMillis();
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("DatePosted", date + "");
                            e.apply();
                            try {
                                Log.d("Location Set", "onResponse: Location has been set with affected rows :" + response.getString("affectedRows"));
                            } catch (JSONException je) {
                                Log.d("Location Set", "onException: " + je.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Location Set", "onError: " + error.getMessage());
                        }
                    });
                    RequestQueueSingleton.getInstance(this).getRequestQueue().add(jar);
                } else {
                    Utilities.MakeToast(this, "Unable to get location");
                }

            } catch (SecurityException e) {
                Log.d("HomeActivity", e.getMessage());
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 1);

            }

        }

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
        if (id == R.id.action_post) {
            startPostActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void startPostActivity() {
        startActivity(new Intent(this, PostActivity.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
            String userID = "";
            String alias = "";
            userID = sp.getString("userID", userID);
            alias = sp.getString("alias", alias);
            Intent i = new Intent(this, ProfileActivity.class);
            i.putExtra("id", userID);
            i.putExtra("name", alias);
            i.putExtra("IsOwner", true);
            startActivity(i);
        } else if (id == R.id.nav_event) {
            startActivity(new Intent(this, EventActivity.class));
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.remove("userID");
            e.remove("alias");
            e.apply();
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
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Utilities.MakeSnack(findViewById(R.id.cLayout), "Unable to connect to Google Play Services");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean Result = sp.getBoolean(SettingsActivity.LOCATIONKEY, false);
        if (Result) {
            UpdateLocation();
        }
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = sp.getString("userID", "");
        NewsFeedUtil.makeRequest(id);
        MessengerUtil.makeRequest(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UpdateLocation();
            }
        }
    }

}
