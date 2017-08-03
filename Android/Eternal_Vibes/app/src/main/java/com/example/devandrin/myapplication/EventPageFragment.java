package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventPageFragment extends Fragment {
    public static final String DATA = "theskyisreadbruh";
    private int p;

    public static EventPageFragment newInstance(int Page) {
        Bundle args = new Bundle();
        args.putInt(DATA, Page);
        EventPageFragment f = new EventPageFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //This is allows a context menu to be used
        p = getArguments().getInt(DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;
        switch (p) {
            case 1:
                v = RenderFactory.getClass("Events", inflater, container).displayContent();
                break;
            case 2:
                v = RenderFactory.getClass("PersonalEvents", inflater, container).displayContent();
                break;
            case 3:
                v = RenderFactory.getClass("Events", inflater, container).displayContent();
                break;
        }
        return v;
    }
}
