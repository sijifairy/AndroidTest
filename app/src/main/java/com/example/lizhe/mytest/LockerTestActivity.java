package com.example.lizhe.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by lz on 3/23/17.
 */

public class LockerTestActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        }, 1000 * 10);

        View view = new View(LockerTestActivity.this);
        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        setContentView(view);
    }
}
