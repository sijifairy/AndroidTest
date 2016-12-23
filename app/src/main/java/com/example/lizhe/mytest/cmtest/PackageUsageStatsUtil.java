package com.example.lizhe.mytest.cmtest;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class PackageUsageStatsUtil {

    private static final String PKG_USAGE_SETTING_ACTION = "android.settings.USAGE_ACCESS_SETTINGS";

    @SuppressLint({"NewApi"})
    public static boolean checkUsageAccessEnable(Context context) {
        if (context == null || !isIntentExist(context, new Intent(PKG_USAGE_SETTING_ACTION)) || Build.VERSION.SDK_INT < 21) {
            return true;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            return ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName) == 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    @SuppressLint({"NewApi"})
    public static boolean isUsageAccessEnable(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 21) {
            return true;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            return ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName) == 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (RuntimeException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean goToPkgUsageSetting(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        Intent intent = new Intent(PKG_USAGE_SETTING_ACTION);
        if (!isIntentExist(context, intent)) {
            return false;
        }
        startActivity(context, intent);
        return true;
    }

    public static boolean isIntentExist(Context context, Intent intent) {
        if (intent == null || context.getPackageManager().resolveActivity(intent, 0) == null) {
            return false;
        }
        return true;
    }

    public static boolean startActivity(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}