package com.example.lizhe.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hugo.weaving.DebugLog;

public class TextViewEffectiveTest extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_effective_test);
        tvTest = (TextView) findViewById(R.id.tv_test);

        testEffective();
    }

    @DebugLog
    private void testEffective() {
        for (int i = 0; i < 1000; i++) {
            tvTest.setText(String.valueOf(i));
        }
    }
}
