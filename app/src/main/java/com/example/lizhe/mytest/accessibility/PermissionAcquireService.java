package com.example.lizhe.mytest.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_GENERIC;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class PermissionAcquireService extends AccessibilityService {
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS =
            "enabled_notification_listeners";
    /** Cache of enabled notification listener components */
    private static final Object sEnabledNotificationListenersLock = new Object();
    /** Guarded by {@link #sEnabledNotificationListenersLock} */
    private static String sEnabledNotificationListeners;
    /** Guarded by {@link #sEnabledNotificationListenersLock} */
    private static Set<String> sEnabledNotificationListenerPackages = new HashSet<String>();

    private AccessibilityServiceInfo mAccessibilityServiceInfo;

    String back ;
    String home ;
    String recent ;

    @Override public void onCreate() {
        super.onCreate();

        super.onCreate();
//        back = getVitualNavigationKey(this, "accessibility_back", "com.android.systemui", "");
//        home = getVitualNavigationKey(this, "accessibility_home", "com.android.systemui", "");
//        recent = getVitualNavigationKey(this, "accessibility_recent", "com.android.systemui", "");

        mAccessibilityServiceInfo=new AccessibilityServiceInfo();
        mAccessibilityServiceInfo.feedbackType=FEEDBACK_GENERIC;
        mAccessibilityServiceInfo.eventTypes=AccessibilityEvent.TYPE_VIEW_CLICKED|AccessibilityEvent.TYPE_VIEW_LONG_CLICKED|AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        int flag=0;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            flag=flag|AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2){
            flag=flag|AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
        }
        mAccessibilityServiceInfo.flags=flag;
        mAccessibilityServiceInfo.notificationTimeout=100;
        setServiceInfo(mAccessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("permission", "on accessibility event " + event);
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                String className = event.getClassName().toString();
//                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
//                    getPacket();
//                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
//                    openPacket();
//                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
//                    close();
//                }
                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                if (nodeInfo != null) {
                    List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/list");
                    List<AccessibilityNodeInfo> buttons = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/button1");
                    nodeInfo.recycle();
//                    for (AccessibilityNodeInfo item : list) {
//                        item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    }
                    if (!isNewsAccessSettingsOn()) {
                        try {
                            list.get(1).getChild(5).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (buttons.size() > 0) {
                        buttons.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    public boolean isNewsAccessSettingsOn() {
        Context context = getApplicationContext();
        Set<String> enabledPackages = NotificationManagerCompat.getEnabledListenerPackages(context);
        return enabledPackages.contains(context.getPackageName());
    }

    /**
     * Get the set of packages that have an enabled notification listener component within them.
     */
    public static Set<String> getEnabledListenerPackages(Context context) {
        final String enabledNotificationListeners = Settings.Secure.getString(
                context.getContentResolver(),
                SETTING_ENABLED_NOTIFICATION_LISTENERS);
        // Parse the string again if it is different from the last time this method was called.
        if (enabledNotificationListeners != null
                && !enabledNotificationListeners.equals(sEnabledNotificationListeners)) {
            final String[] components = enabledNotificationListeners.split(":");
            Set<String> packageNames = new HashSet<String>(components.length);
            for (String component : components) {
                ComponentName componentName = ComponentName.unflattenFromString(component);
                if (componentName != null) {
                    packageNames.add(componentName.getPackageName());
                }
            }
            synchronized (sEnabledNotificationListenersLock) {
                sEnabledNotificationListenerPackages = packageNames;
                sEnabledNotificationListeners = enabledNotificationListeners;
            }
        }
        return sEnabledNotificationListenerPackages;
    }
}
