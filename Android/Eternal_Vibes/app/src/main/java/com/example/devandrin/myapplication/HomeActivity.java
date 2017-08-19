package com.example.devandrin.myapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private ProgressBar load = null;
    private ProgressBar radarProgressBar;
    private static HomeActivity instance = null;
    private static DBHelper dbHelper = null;
    FloatingActionButton newChatFab, newPostFab, btn_sortRadar;

    private RadarContent rcObjItem = new RadarContent();
    public static String activeuserID = "";
    private ArrayList<RadarContent> unsortedRadarList = new ArrayList<>();

    static HomeActivity getInstance() {
        return instance;
    }

    EVService service;
    boolean isBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            EVService.MessengerBinder binder = (EVService.MessengerBinder) service;
            HomeActivity.this.service = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            isBound = false;
        }
    };
    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(DBHelper dbHelper) {
        HomeActivity.dbHelper = dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        load = (ProgressBar) findViewById(R.id.pbLoad);
        if(dbHelper == null)
        {
            dbHelper = new DBHelper(getApplicationContext());
        }
        instance = this;

        //The activeuserID is used in the RadarUtil class to make Requests
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String activeuserID = sp.getString("userID", "");

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

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vpager);
        final TabAdapter ta = new TabAdapter(getSupportFragmentManager(), HomeActivity.this);
        viewPager.setAdapter(ta);

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
                } else {
                    newPostFab.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 2) {
                    newChatFab.setVisibility(View.GONE);
                    newPostFab.setVisibility(View.GONE);
                    btn_sortRadar.setVisibility(View.VISIBLE);
                    //enableProgressBar();
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
        Intent i = new Intent(this,EVService.class);
        startService(i);
        refreshNewsFeed();
    }

    public ProgressBar getProgressBar(){
        return load;
    }

    /* Gets the response from the async task. Please do not delete this
    @Override
    public ArrayList<RadarContent> processCompleted(ArrayList<RadarContent> rcList){
        this.unsortedRadarList = rcList;
        return this.unsortedRadarList;
    }*/

    public ArrayList<RadarContent> getUnsortedRadarList(){
        return unsortedRadarList;
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
    public void enableProgressBar()
    {
        if (load.getVisibility() == View.GONE) {
            load.setVisibility(View.VISIBLE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.cLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(false);
                }
            }
        }
    }
    public void disableProgressBar()
    {
        if (load.getVisibility() == View.VISIBLE) {
            load.setVisibility(View.GONE);
            ConstraintLayout c = (ConstraintLayout) findViewById(R.id.cLayout);
            for(int i =0; i< c.getChildCount() ; i++)
            {
                View v = c.getChildAt(i);
                if(v.getId() != load.getId())
                {
                    v.setEnabled(true);
                }
            }
        }
    }
    /**
     * The following methods are used to setup a floating context menu.
     *
     * @param lv which is a listview from the RadarUtil class
     */
    public void setupRadarProfileMenu(ListView lv) {
        registerForContextMenu(lv);
        openContextMenu(lv);
        unregisterForContextMenu(lv);
    }

    public void setRadarProfileObject(RadarContent rcObjItem){
        //Create a branch new RadarContent object
        //The data was passed from RadarUtil
        this.rcObjItem.setUserID(rcObjItem.getUserID());
        this.rcObjItem.setsUsername(rcObjItem.getsUsername());
        this.rcObjItem.setsLastName(rcObjItem.getsLastName());
        this.rcObjItem.setsAlias(rcObjItem.getsAlias());
        this.rcObjItem.setRanking(rcObjItem.getRanking());
        this.rcObjItem.setRating(rcObjItem.getRating());
        this.rcObjItem.setDistance(rcObjItem.getDistance());
        this.rcObjItem.setsLocation(rcObjItem.getsLocation());
        this.rcObjItem.setsEmail(rcObjItem.getsEmail());
        this.rcObjItem.setSkillset(rcObjItem.getSkillset());
        this.rcObjItem.setDescription(rcObjItem.getDescription());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose an option");
        MenuInflater inflater = getMenuInflater();

        //Check the view that was passed in to determine which menu to inflate
        if(v.getId() == R.id.ArrayList){
            inflater.inflate(R.menu.radar_profile_menu, menu);
        }
        else
        {
            inflater.inflate(R.menu.sort_options_menu, menu);
        }
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

            /*
            case R.id.HighestRating:
                RadarUtil.UpdatedSort_RadarProfiles("RATING", false);
                return true;

            case R.id.LowestRating:
                RadarUtil.UpdatedSort_RadarProfiles("RATING", true);
                return true;

            case R.id.InviteToEvent:
                Toast.makeText(HomeActivity.getInstance(), "An invitation has been sent to the selected user.",
                        Toast.LENGTH_LONG).show();
                //Send an invitation to the user functionality can be added here
                return true;

            case R.id.FollowUser:
                Thread followThread = new Thread(new Runnable(){
                    @Override
                    public void run(){

                        int userIDToFollow = rcObjItem.getUserID();
                        String url = "https://www.eternalvibes.me/followUser/" + activeuserID + "/" + userIDToFollow;
                        JsonObjectRequest jar = new JsonObjectRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        RequestQueueSingleton.getInstance(HomeActivity.getInstance().getApplicationContext()).addToQ(jar);
                    }
                });

                followThread.setDaemon(true);
                followThread.start();

                Toast.makeText(this, "You are now following " + rcObjItem.getsUsername(),
                        Toast.LENGTH_LONG).show();

                return true;*/

            case R.id.ViewProfileDetails:
                //View more of the profile details
                Intent intent = new Intent(HomeActivity.getInstance(), RadarProfileActivity.class);
                //Pass the required information to the next activity
                intent.putExtra("UserID", rcObjItem.getUserID());
                intent.putExtra("Name", rcObjItem.getsUsername());
                intent.putExtra("LastName", rcObjItem.getsLastName());
                intent.putExtra("Alias", rcObjItem.getsAlias());
                intent.putExtra("Rank", rcObjItem.getRanking());
                intent.putExtra("Rating", rcObjItem.getRating());
                intent.putExtra("Distance", rcObjItem.getDistance());
                intent.putExtra("Location", rcObjItem.getsLocation());
                intent.putExtra("Email", rcObjItem.getsEmail());
                intent.putExtra("Skillset", rcObjItem.getSkillset());
                intent.putExtra("Description", rcObjItem.getDescription());
                startActivity(intent);
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
        switch(id)
        {
            case R.id.action_topArtists:
                Intent i = new Intent(this,FollowersListActivity.class);
                i.putExtra("title", "Top Artists");
                startActivity(i);
                return true;
            case R.id.action_post:
                startPostActivity();
                return true;
            case R.id.action_event:
                startActivity(new Intent(this, EventActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
            dbHelper.resetData();
            startActivity(i);
            Intent es = new Intent(this,EVService.class);
            stopService(es);
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
    void refreshNewsFeed()
    {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = sp.getString("userID", "");
        NewsFeedUtil.makeRequest(id);
    }
    @Override
    protected void onResume() {
        Intent intent = new Intent(this, EVService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean Result = sp.getBoolean(SettingsActivity.LOCATIONKEY, false);
        if (Result) {
            UpdateLocation();
        }
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = sp.getString("userID", "");
        activeuserID = id;
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        NotificationManager m = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        m.cancelAll();
        dbHelper.close();
        dbHelper = null;
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
