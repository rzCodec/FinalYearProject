package com.example.devandrin.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    private static Dashboard instance;
    private TextView warning,Register;
    private EditText Email,Password;
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

                hardcodeLogin(); //Testing purposes
                if(Password.length() > 0)
                {
                    if(Email.length()>0)
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
    }//end of login function

    //Temporary login method for unexpected errors
    private void hardcodeLogin(){
        String hardcodeAlias = Alias.getText().toString();
        String hardcodeEmail = Email.getText().toString();
        String hardcodePassword = Password.getText().toString();

        if((hardcodeAlias.equals("admin")) || (hardcodeEmail.equals("admin"))){
            if(hardcodePassword.equals("admin")){
                nextActivity();
            }
        }
    }
    private void requestLogin(final String username, final String password)
    {
        String url = "https://www.eternalvibes.me/MobileLogin";
        Map<String,String> data = new HashMap<>();
        data.put("username",username);
        data.put("password",password);
        JSONObject jo = new JSONObject();
        try{
            jo.put("username",username);
            jo.put("password",password);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                warning.setText("Password correct");
                warning.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                warning.setText("Credentials incorrect!, "+username+" , "+password);
                warning.setVisibility(View.VISIBLE);
            }
        });
        /*JsonArrayRequest jar = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
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
                warning.setText(error.getMessage());
                warning.setVisibility(View.VISIBLE);
            }
        });*/


        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(jor);
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
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("userID", 123 +"" );
        e.putString("alias", "fake alias");
        e.apply();
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
    static Dashboard getInstance() {
        return instance;
    }
}
