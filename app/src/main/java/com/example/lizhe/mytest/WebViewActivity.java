package com.example.lizhe.mytest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {

    private static final String URL = "https://www.google.com.hk/search?q=sbs&hl=en";

    private WebView webView;

    private static final String[] DOM_IDS = {"sfcnt", "qslc"};

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.wb_result);
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //titleview.setText(title);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:(function() { " + spliceDomOperations() + "})()");
            }
        }, 5000);
    }

    private String spliceDomOperations() {
        StringBuilder builder = new StringBuilder();
        for (String domId : DOM_IDS) {
            builder.append("document.getElementById('" + domId + "').style.display='none';");
        }
        return builder.toString();
    }
}
