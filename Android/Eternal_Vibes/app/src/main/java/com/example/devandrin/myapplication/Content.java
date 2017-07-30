package com.example.devandrin.myapplication;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Devandrin on 2017/04/01.
 */

public abstract class Content implements iContent {
    protected LayoutInflater inflater;
    protected ViewGroup container;
    protected SwipeRefreshLayout srl;

    public Content(LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
        this.container = container;
    }

    protected void update() {
        HomeActivity.getInstance().onResume();
        srl.setRefreshing(false);
    }

    protected SwipeRefreshLayout.OnRefreshListener srfListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        };
    }

    @Override
    public View displayContent() {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }
}
