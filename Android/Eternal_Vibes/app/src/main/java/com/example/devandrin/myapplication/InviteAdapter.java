package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
 * Created by Devandrin on 2017/08/06.
 */

public class InviteAdapter extends ArrayAdapter<Profile> {
    static ArrayList<Profile> selectedItems ;

    public InviteAdapter(Context context, ArrayList<Profile> objects) {
        super(context, 0, objects);
        selectedItems = new ArrayList<>();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        InviteAdapter.Components c;
        SharedPreferences sp = SendInviteActivity.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
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
        final View v = convertView;
        /*v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItems.contains(p))
                {
                    selectedItems.remove(p);
                    v.setBackgroundColor(Color.parseColor("#f57c00"));
                }
                else
                {
                    selectedItems.add(p);
                    v.setBackgroundColor(Color.parseColor("#FAFAFA"));
                }
            }
        });*/

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(selectedItems.contains(p))
                {
                    selectedItems.remove(p);
                    v.setBackgroundColor(Color.parseColor("#f57c00"));
                }
                else
                {
                    selectedItems.add(p);
                    v.setBackgroundColor(Color.parseColor("#FAFAFA"));
                }
                return false;
            }
        });
        return convertView;
    }

    private class Components {
        TextView username;
        ImageView iv;
    }

}