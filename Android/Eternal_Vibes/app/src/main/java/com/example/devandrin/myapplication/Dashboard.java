package com.example.devandrin.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    private static Dashboard instance;
    private TextView warning,Register;
    private EditText Email,Alias,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isSeen()) {
            nextActivity();
        } else {
            instance = this;
            PreferenceManager.setDefaultValues(this, R.xml.pref_location_services, true);
            PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);
            setContentView(R.layout.activity_dashboard);
            warning = (TextView) findViewById(R.id.warning);
            warning.setVisibility(View.GONE);
            Register = (TextView) findViewById(R.id.tvRegister);
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getInstance(),RegistrationActivity.class));
                }
            });
            Email = (EditText) findViewById(R.id.EmailAddress);
            Alias = (EditText) findViewById(R.id.Alias);
            Password = (EditText) findViewById(R.id.Password);
            Button btnLogin = (Button)findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(login());
        }

    }
    private View.OnClickListener login()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Password.length() > 0)
                {
                    if(Alias.length()>0)
                    {
                        requestLogin(Alias.getText().toString(),Password.getText().toString());
                    }
                    else if(Email.length()>0)
                    {
                        if(Patterns.EMAIL_ADDRESS.matcher(Email.getText()).matches())
                        {
                            Email.setTextColor(Color.BLACK);
                            requestLogin(Email.getText().toString(),Password.getText().toString());
                        }
                        else
                        {
                            Email.setTextColor(Color.RED);
                        }
                    }
                    else
                    {
                        Utilities.MakeToast(getApplicationContext(),"Credentials empty");
                    }
                }
                else
                {
                    Utilities.MakeToast(getApplicationContext(),"Credentials empty");
                }
            }
        };
    }
    private void requestLogin(String username, String password)
    {
        String url = "https://eternalvibes.me/login/"+username+"/"+password;
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    nextActivity(response.getJSONObject(0));
                }
                catch(JSONException e)
                {
                    warning.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                warning.setVisibility(View.VISIBLE);
            }
        });

        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(jar);
    }
    void nextActivity(JSONObject obj)  throws JSONException
    {

        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("userID",obj.getInt("id")+"" );
        e.putString("alias", obj.getString("alias"));
        e.apply();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
    private void nextActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private boolean isSeen() {
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        return sp.contains("userID");
    }

    @Override
    protected void onResume() {
        super.onResume();
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            Dialog err = GoogleApiAvailability.getInstance().getErrorDialog(this, result, 0);
            err.show();
        }
    }
    public static Dashboard getInstance() {
        return instance;
    }
}
