package com.example.lizhe.mytest;

import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        }
    }
}
