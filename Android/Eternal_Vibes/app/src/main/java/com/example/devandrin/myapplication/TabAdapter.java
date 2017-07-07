package com.example.devandrin.myapplication;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/**
 * Created by Devandrin on 2017/03/27.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Messenger", "News Feed", "Radar"};
    private Context context;
    FloatingActionButton fab = null;
    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        fab = HomeActivity.getInstance().newChatFab;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        return tabTitles[position];
    }
}