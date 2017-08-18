package com.example.devandrin.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ronald on 8/16/2017.
 */

public class GeneralPageFragment extends PageFragment {
    public static final String DATA = "theprofileisblue";
    public static String sActivityType = "Replace this with the name of your Activity such as HomeActivty";
    private int p;

    public static GeneralPageFragment newInstance(int Page) {
        Bundle args = new Bundle();
        args.putInt(DATA, Page);
        GeneralPageFragment GPF = new GeneralPageFragment();
        GPF.setArguments(args);
        return GPF;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getArguments().getInt(DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (p) {
            case 1:
                if(sActivityType.equals("RadarProfileActivity")){
                    view = RenderFactory.getClass("RadarProfile", inflater, container).displayContent();
                }
                else if(sActivityType.equals("HomeActivity")){

                }
                break;

            case 2:
                if(sActivityType.equals("RadarProfileActivity")){
                    view = RenderFactory.getClass("RadarProfileEventList", inflater, container).displayContent();
                }
                break;

        }
        return view;
    }
}
