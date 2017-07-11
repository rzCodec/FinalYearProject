package com.example.devandrin.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Devandrin on 2017/07/11.
 */

public class EventTabAdapter extends FragmentPagerAdapter
{
    private final int PAGE_COUNT = 3;
    private String[] tabTitles = new String[]{"Events", "Your Events", "Pending Reviews"};
    private Context context;
    @Override
    public Fragment getItem(int position) {
        return EventPageFragment.newInstance(position+1);
    }
    public EventTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
