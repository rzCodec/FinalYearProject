package com.example.devandrin.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Dashboard extends AppCompatActivity {
    private static Dashboard instance;
    private TextView warning;
    private EditText Email, Password;
    private static final String url = "https://www.eternalvibes.me/MobileLogin";
    static Dashboard getInstance() {
        return instance;
    }

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
            TextView Register = (TextView) findViewById(R.id.tvRegister);
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getInstance(), RegistrationActivity.class));
                }
            });
            Email = (EditText) findViewById(R.id.EmailAddress);
            Password = (EditText) findViewById(R.id.Password);
            Button btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(login());
        }

    }

    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> data = new HashMap<>();
                //data.put("user_id",303346727);
                //data.put("username","Vanneh");
                data.put("user_id",303346716);
                data.put("username","kogmaw");
                try
                {
                    nextActivity(new JSONObject(data));
                }catch(JSONException e)
                {
                    e.printStackTrace();
                }


                if (Password.length() > 0) {
                    if (Email.length() > 0) {
                        if (Patterns.EMAIL_ADDRESS.matcher(Email.getText()).matches()) {
                            Email.setTextColor(Color.BLACK);
                            requestLogin(Email.getText().toString(), Password.getText().toString());
                        } else {
                            Email.setTextColor(Color.RED);
                        }
                    } else {
                        Utilities.MakeToast(getApplicationContext(), "Credentials empty");
                    }
                } else {
                    Utilities.MakeToast(getApplicationContext(), "Credentials empty");
                }

            }
        };
    }

    private void requestLogin(final String username, final String password) {

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        final JSONObject jo = new JSONObject(data);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("200"))
                {
                    nextActivity(username);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 500) {
                    warning.setText("Error Code 500");
                    warning.setVisibility(View.VISIBLE);
                } else {
                    warning.setText("Unknown Error code");
                    warning.setVisibility(View.VISIBLE);
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jo.toString() == null ? null : jo.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jo.toString(), "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = response.statusCode+"";
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }


        };

        /*JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    nextActivity(response);
                }
                catch(JSONException e)
                {
                    warning.setText("JSON error");
                    warning.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse.statusCode == 200) {
                        warning.setText("Success");
                        warning.setVisibility(View.VISIBLE);
                    } else if (error.networkResponse.statusCode == 500) {
                        warning.setText("Error Code 500");
                        warning.setVisibility(View.VISIBLE);
                    } else {
                        warning.setText("Unknown Error code");
                        warning.setVisibility(View.VISIBLE);
                    }
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Map<String,String> rHeaders =response.headers;
                String raw = rHeaders.get("Set-Cookie");
                warning.setText(raw);
                warning.setVisibility(View.VISIBLE);
                return super.parseNetworkResponse(response);
            }
        };*/


        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(sr);
        warning.setText("Login requested");
    }
    private void nextActivity(String email)
    {
        String emailurl = "https://www.eternalvibes.me/getuserinfoemail/"+email.toString();
        /*JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, emailurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try
               {
                   nextActivity(response);
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });*/
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, emailurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    nextActivity(response.getJSONObject(0));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(jar);
        warning.setText("Email Requested");
        warning.setVisibility(View.VISIBLE);
    }
    void nextActivity(JSONObject obj) throws JSONException {

        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("userID", obj.getInt("id") + "");
        e.putString("alias", obj.getString("username"));
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
}