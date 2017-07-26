package com.example.devandrin.myapplication;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.Inflater;

/**
 * Created by Devandrin on 2017/07/12.
 */

public class ContactChatAdapter extends ArrayAdapter<Profile> {
    public ContactChatAdapter( Context context, ArrayList<Profile> objects) {
        super(context,0,objects);
    }
    class Components
    {
        TextView username;
        ImageView iv;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Components c;
        if(convertView == null)
        {
            c = new Components();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
            c.username = (TextView) convertView.findViewById(R.id.userName);
            c.iv = (ImageView) convertView.findViewById(R.id.iv_dpicture);
            convertView.setTag(c);
        }
        else
        {
            c = (Components) convertView.getTag();
        }
        final Profile p = getItem(position);
        c.username.setText(p.getAlias());
        c.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.MakeToast(getContext(),"Created Chat with user id : "+p.getId());
            }
        });
        switch(p.getGenre_id())
        {
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
}
