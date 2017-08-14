package com.example.devandrin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/08/07.
 */

public class FollowerAdapter extends ContactChatAdapter {
    public FollowerAdapter(Context context, ArrayList<Profile> objects) {
        super(context, objects);
    }

    @Override
    protected View.OnClickListener sendRequest(final Profile p) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FollowersListActivity.getInstance(),ProfileActivity.class);
                i.putExtra("name", p.getAlias());
                i.putExtra("id",p.getId()+"");
                FollowersListActivity.getInstance().startActivity(i);
            }
        };
    }
}
