package com.example.devandrin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private TextView Genre, Firstname, LastName, Email, PasswordE, PasswordC, Alias,description;
    private ArrayList<String> list = new ArrayList<>();
    private RegistrationActivity instance;
    private ProgressDialog pd;
    private final String url = "https://www.eternalvibes.me/mobilesignup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        instance = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Genre")
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Genre.setText(which + "");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog ad = builder.create();
        Genre = (TextView) findViewById(R.id.Genre);
        Email = (TextView) findViewById(R.id.rEmail);
        Alias = (TextView) findViewById(R.id.rAlias);
        Firstname = (TextView) findViewById(R.id.firstName);
        LastName = (TextView) findViewById(R.id.lastName);
        PasswordC = (TextView) findViewById(R.id.PasswordC);
        PasswordE = (TextView) findViewById(R.id.PasswordE);
        description = (TextView) findViewById(R.id.txtDescription);
        Genre.setKeyListener(null);
        Genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
        genreList();
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(Register());
    }

    private View.OnClickListener Register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Firstname.length() <= 0) {
                    Firstname.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (LastName.length() <= 0) {
                    LastName.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (Email.length() <= 0) {
                    Email.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (Alias.length() <= 0) {
                    Alias.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (PasswordC.length() <= 0) {
                    PasswordC.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (PasswordE.length() <= 0) {
                    PasswordE.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (!PasswordC.getText().toString().equals(PasswordE.getText().toString())) {
                    PasswordC.setBackgroundResource(R.drawable.warningbox);
                    PasswordE.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
                    Email.setBackgroundResource(R.drawable.warningbox);
                    return;
                }
                StartProgressDialog();
                Map<String,Object> datat = new HashMap<>();
                    datat.put("firstname", Firstname.getText().toString());
                    datat.put("surname", LastName.getText().toString());
                    datat.put("realUserName", Alias.getText().toString());
                    datat.put("username", Email.getText().toString());
                    datat.put("password", PasswordC.getText().toString());
                    datat.put("skill_id",1);
                    datat.put("genre_id", Integer.parseInt(Genre.getText().toString()));
                    datat.put("distance_id", 4);
                if(description.getText().length() >0)
                {
                    datat.put("description", description.getText().toString());
                } else
                {
                    datat.put("description", "nothing for now");
                }
                final JSONObject data = new JSONObject(datat);
                /*JsonObjectRequest jar = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SharedPreferences sp = Dashboard.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("userID", response.getInt("id") + "");
                            e.putString("alias", response.getString("username"));
                            e.apply();
                            Intent i = new Intent(instance, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            Log.d("Error", "Error");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utilities.MakeToast(instance,"Something went wrong");
                    }
                });*/
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("200"))
                        {
                            nextActivity(Email.getText().toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return data.toString() == null ? null : data.toString().getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", data.toString(), "utf-8");
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
                RequestQueueSingleton.getInstance(instance).addToQ(sr);
            }
        };
    }

    private void genreList() {
        String url = "https://eternalvibes.me/getgenres";
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getJSONObject(i).getString("name"));

                    }
                } catch (JSONException e) {

                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(jar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void nextActivity(String email) {
        String emailurl = "https://www.eternalvibes.me/getuserinfoemail/" + email.toString();
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
                try {
                    nextActivity(response.getJSONObject(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
            }
        });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToQ(jar);
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
    private void StartProgressDialog()
    {
        pd = new ProgressDialog(instance);
        pd.setMessage("Uploading and Registering your details");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        pd.show();
    }
}
