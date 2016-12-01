package com.example.lizhe.mytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LanguageChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("language", "on receive " + intent.getAction() + " language is " + context.getResources().getConfiguration().locale);
        Log.d("language", "on receive " + intent.getAction() + " language list is " + context.getResources().getConfiguration().getLocales().get(0));
    }
}
