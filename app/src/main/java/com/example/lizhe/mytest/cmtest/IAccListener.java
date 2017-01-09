package com.example.lizhe.mytest.cmtest;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

public interface IAccListener {
    void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);

    void onCreate(AccessibilityService accessibilityService);

    void onInterrupt();

    void onServiceConnected();

    boolean onUnbind(Intent intent);
}