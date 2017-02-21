package com.example.lizhe.mytest.bottomnavigation;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lizhe.mytest.R;

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
    }
}
