package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/07/12.
 */

public class ContactChatAdapter extends ArrayAdapter<Profile> {
    public ContactChatAdapter(Context context, ArrayList<Profile> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Components c;
        SharedPreferences sp = NewChatActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String userID = sp.getString("userID", "");
        if (convertView == null) {
            c = new Components();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
            c.username = (TextView) convertView.findViewById(R.id.userName);
            c.username.setTextColor(HomeActivity.getInstance().getResources().getColor(R.color.colorPrimary));
            c.iv = (ImageView) convertView.findViewById(R.id.iv_dpicture);
            convertView.setTag(c);
        } else {
            c = (Components) convertView.getTag();
        }
        final Profile p = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = HomeActivity.getInstance().getDbHelper();
                Chat c = dbh.hasChat(p.getId());
                if(c != null)
                {
                    Intent i = new Intent(HomeActivity.getInstance(),ChatActivity.class);
                    i.putExtra("ChatID",c.ChatID);
                    i.putExtra("Name",p.getAlias());
                    HomeActivity.getInstance().startActivity(i);
                    NewChatActivity.getInstance().finish();
                    return;
                }
                String url = "https://eternalvibes.me/createchat/"+userID+"/"+p.getId();
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            if(response.getInt("affectedRows") == 1)
                            {
                                Intent i = new Intent(HomeActivity.getInstance(),ChatActivity.class);
                                i.putExtra("ChatID",response.getInt("insertId"));
                                i.putExtra("Name",p.getAlias());
                                HomeActivity.getInstance().startActivity(i);
                                NewChatActivity.getInstance().finish();
                            }
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utilities.MakeToast(NewChatActivity.getInstance().getApplicationContext(),"Eish");
                    }
                });
                RequestQueueSingleton.getInstance(NewChatActivity.getInstance()).addToQ(jor);

            }
        });
        c.username.setText(p.getAlias());
        switch (p.getGenre_id()) {
            case 0:
                c.iv.setImageResource(R.mipmap.jazz_icon);
                break;
            case 1:
                c.iv.setImageResource(R.mipmap.rock_icon);
                break;
            case 2:
                c.iv.setImageResource(R.mipmap.rap_icon);
                break;
        }
        return convertView;
    }

    private class Components {
        TextView username;
        ImageView iv;
    }

}
