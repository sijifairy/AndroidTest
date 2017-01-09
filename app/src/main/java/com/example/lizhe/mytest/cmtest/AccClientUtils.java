package com.example.lizhe.mytest.cmtest;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

public class AccClientUtils {
    public static boolean isAccSwitchOn(Context context) {
        boolean z;
        if (VERSION.SDK_INT > 14) {
            AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
            if (accessibilityManager != null) {
                List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(16);
                if (!(enabledAccessibilityServiceList == null || enabledAccessibilityServiceList.isEmpty())) {
                    for (AccessibilityServiceInfo resolveInfo : enabledAccessibilityServiceList) {
                        ResolveInfo resolveInfo2 = resolveInfo.getResolveInfo();
                        if (resolveInfo2 != null) {
                            ServiceInfo serviceInfo = resolveInfo2.serviceInfo;
                            if (!(serviceInfo == null || TextUtils.isEmpty(serviceInfo.packageName) || !context.getPackageName().equals(serviceInfo.packageName))) {
                                z = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        z = false;
        if (z) {
            return z;
        }
        return checkAccSwitch(context);
    }

    private static boolean checkAccSwitch(Context context) {
        String string = Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        String packageName = context.getPackageName();
        String name = "AccessibilityKillService";
        String[] split = string.split( ":");
        if (split == null || split.length <= 0) {
            return false;
        }
        for (String obj : split) {
            if (!TextUtils.isEmpty(obj)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(obj);
                if (unflattenFromString != null) {
                    String packageName2 = unflattenFromString.getPackageName();
                    String className = unflattenFromString.getClassName();
                    if (packageName.equals(packageName2) && name.equals(className)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }
}