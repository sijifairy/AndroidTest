package com.example.lizhe.mytest;

import android.app.Application;
import android.content.Context;

/**
 * Created by lz on 17/1/14.
 */

public class MyTestApplication extends Application {

    private static MyTestApplication instance;
    private static Context mContext;

    public MyTestApplication() {
    }

    private static MyTestApplication getInstance() {
        if(instance == null) {
            instance = new MyTestApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
