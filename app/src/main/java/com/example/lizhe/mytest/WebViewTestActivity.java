package com.example.lizhe.mytest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.view.View.SCROLLBARS_INSIDE_OVERLAY;

public class WebViewTestActivity extends AppCompatActivity {

    private static final String SEARCH_TAG = "WebViewTestActivity";
    private static final String[] HIDE_DOM_IDS = {"page-hd", "page-tips"};
    private static final String BAIDU_SEARCH_URL = "https://www.baidu.com/s?wd=web%20view%20test";

    private WebView mWebView;
    private WebChromeClient mSearchChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d(SEARCH_TAG, "on page progress changed and progress is " + newProgress);
            if (newProgress == 100) {
                mWebView.loadUrl(getDomOperationStatements(HIDE_DOM_IDS));
            }
        }
    };
    private WebViewClient mSearchClient = new WebViewClient() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(SEARCH_TAG, "on page loading url is " + url);
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(this);
        setContentView(mWebView);

        mWebView.setWebChromeClient(mSearchChromeClient);
        mWebView.setWebViewClient(mSearchClient);
        configWebViewSettings(mWebView, this, false);

        mWebView.loadUrl(BAIDU_SEARCH_URL);
    }

public static String getDomOperationStatements(String[] hideDomIds) {
    StringBuilder builder = new StringBuilder();
    // add javascript prefix
    builder.append("javascript:(function() { ");

    for (String domId : hideDomIds) {
        builder.append("var item = document.getElementById('").append(domId).append("');");
        builder.append("item.parentNode.removeChild(item);");
    }
    // add javascript suffix
    builder.append("})()");
    return builder.toString();
}

    public static void configWebViewSettings(WebView webView, Context context, boolean supportMultiWindow) {
        WebSettings settings = webView.getSettings();

        // Enable JS
        try {
            settings.setJavaScriptEnabled(true);
        } catch (NullPointerException e) {
            // See https://code.google.com/p/android/issues/detail?id=40944
            e.printStackTrace();
        }
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSaveFormData(true);
        settings.setDatabaseEnabled(true);

        // Enable page adaptation
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        PackageManager packageManager = context.getPackageManager();
        boolean supportMultiTouch = packageManager.hasSystemFeature("android.hardware.touchscreen.multitouch")
                || packageManager.hasSystemFeature("android.hardware.faketouch.multitouch.distinct");
        settings.setDisplayZoomControls(!supportMultiTouch);
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT < 18) {

            settings.setAppCacheMaxSize(Long.MAX_VALUE);
        }
        if (Build.VERSION.SDK_INT < 17) {
            settings.setEnableSmoothTransition(true);
        }
        if (Build.VERSION.SDK_INT > 16) {
            settings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT < 19) {
            settings.setDatabasePath(context.getCacheDir() + "/databases");
        }
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(context.getCacheDir().toString());
        settings.setGeolocationDatabasePath(context.getFilesDir().toString());
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setAllowContentAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT > 16) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setSupportMultipleWindows(supportMultiWindow);
    }
}
