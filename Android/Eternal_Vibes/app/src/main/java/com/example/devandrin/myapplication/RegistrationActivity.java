package com.example.devandrin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {
    private TextView Genre, Firstname, LastName, Email, PasswordE, PasswordC, Alias;
    private ArrayList<String> list = new ArrayList<>();
    private RegistrationActivity instance;
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

                JSONObject data = new JSONObject();
                try {
                    data.put("firstname", Firstname.getText());
                    data.put("surname", LastName.getText());
                    data.put("realUserName", Alias.getText());
                    data.put("username", Email.getText());
                    data.put("password", PasswordC.getText());
                    data.put("genre", Genre.getText());
                    data.put("distance", "25");
                    data.put("description", "nothing for now");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jar = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
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
                });
                RequestQueueSingleton.getInstance(instance).addToQ(jar);
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
}
