package com.example.devandrin.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ronald on 8/16/2017.
 */

public class GeneralTabAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT = 0;
    private String[] tabTitles;
    private Context context;

    public GeneralTabAdapter(FragmentManager fm, Context context, int PAGE_COUNT, String... paramtabTitles) {
        super(fm);
        this.context = context;
        this.PAGE_COUNT = PAGE_COUNT;
        tabTitles = new String[PAGE_COUNT];

        for(int i = 0; i < tabTitles.length; i++) {
            tabTitles[i] = paramtabTitles[i];
        }
    }

    @Override
    public Fragment getItem(int position) {
        return GeneralPageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}


