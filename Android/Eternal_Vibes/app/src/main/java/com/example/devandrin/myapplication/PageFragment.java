package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Devandrin on 2017/03/27.
 */

public class PageFragment extends Fragment {
    public static final String DATA = "FishnetStocking";

    private int p;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(DATA, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getArguments().getInt(DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch(p)
        {
            case 1:
                view =RenderFactory.getClass("Messenger",inflater,container).displayContent();
                break;
            case 2:

                view =RenderFactory.getClass("NewsFeed",inflater,container).displayContent();
                break;
            case 3:
                view =RenderFactory.getClass("Radar",inflater,container).displayContent();
                break;
        }
        return view;
    }

}
