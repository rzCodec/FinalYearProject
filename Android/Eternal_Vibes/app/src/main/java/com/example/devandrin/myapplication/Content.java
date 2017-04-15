package com.example.devandrin.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Devandrin on 2017/04/01.
 */

public abstract class Content implements  iContent
{
     protected LayoutInflater inflater;
     protected ViewGroup container;

     public Content(LayoutInflater inflater,ViewGroup container) {
          this.inflater=inflater;
          this.container=container;
     }
}
