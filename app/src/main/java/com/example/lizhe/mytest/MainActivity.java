package com.example.lizhe.mytest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lizhe.mytest.cmtest.PackageUsageStatsUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.web).setOnClickListener(this);
        findViewById(R.id.web1).setOnClickListener(this);
        findViewById(R.id.web2).setOnClickListener(this);
        findViewById(R.id.contacts).setOnClickListener(this);
        findViewById(R.id.contacts_system_search).setOnClickListener(this);
        findViewById(R.id.speech).setOnClickListener(this);
        findViewById(R.id.texture_test).setOnClickListener(this);
        findViewById(R.id.accessibility_test).setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
        registerReceiver(new LanguageChangeReceiver(), filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.web1:
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("delay", 300);
                startActivity(intent);
                break;
            case R.id.web2:
                intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("delay", 600);
                startActivity(intent);
                break;
            case R.id.contacts:
                startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                break;
            case R.id.contacts_system_search:
                intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType("vnd.android.cursor.dir/postal-address");
                startActivity(intent);
                break;
            case R.id.speech:
                startActivity(new Intent(MainActivity.this, SpeechTestActivity.class));
                break;
            case R.id.texture_test:
                startActivity(new Intent(MainActivity.this, TextureViewTestActivity.class));
                break;
            case R.id.accessibility_test:
//                PackageUsageStatsUtil.goToPkgUsageSetting(this);
                applyForPermission();
                break;
        }
    }

    public void applyForPermission() {
        // Don't use Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS as this constant is not included before API 22
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivitySafely(MainActivity.this, intent);
    }

    public static void startActivitySafely(Context context, Intent intent) {
        try {
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException | NullPointerException e) {

        }
    }
}
