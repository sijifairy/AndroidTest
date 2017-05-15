package com.example.lizhe.mytest.bottomnavigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lizhe.mytest.LockerTestActivity;
import com.example.lizhe.mytest.R;
import com.example.lizhe.mytest.TestAffinity;
import com.example.lizhe.mytest.tablayout.TabLayout;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        final ColorTransitionView colorTransitionView = (ColorTransitionView) findViewById(R.id.clip_bottom_view);
        colorTransitionView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (event.getRawX() <= colorTransitionView.getMeasuredWidth() / 3) {
                        colorTransitionView.startColorTransition(0);
                    } else if (event.getRawX() <= colorTransitionView.getMeasuredWidth() * 2 / 3) {
                        colorTransitionView.startColorTransition(1);
                    } else {
                        colorTransitionView.startColorTransition(2);
                    }
                }
                return true;
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                startActivity(new Intent(BottomNavigationActivity.this, LockerTestActivity.class));
            }
        }, filter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("11111");
        tabLayout.addTab(tab);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("22222");
        tabLayout.addTab(tab1);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(BottomNavigationActivity.this, TestAffinity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("result", "requestcode " + resultCode + " resultcode" + resultCode);
    }

    @Override public void onAttachedToWindow() {
        super.onAttachedToWindow();

        int a = 1;
    }

    @Override public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        int b = 1;
    }
}
