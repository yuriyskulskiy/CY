package com.nst.cropio.yield;


import android.app.Application;
import android.content.Context;

public class YieldApplication extends Application {

    private static YieldApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static Context get(){
        return  sInstance;
    }
}
