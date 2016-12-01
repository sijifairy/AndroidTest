package com.example.lizhe.mytest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private static final String URL = "https://www.google.com.hk/search?q=";

    private static final String[] DOM_IDS = {"sfcnt", "qslc"};

    private WebView webView;
    private EditText search;
    private String searchString = "tom";
    private int delay;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if (getIntent() != null) {
            delay = getIntent().getIntExtra("delay", 0);
        }

        webView = (WebView) findViewById(R.id.wb_result);
        search = (EditText) findViewById(R.id.search);
        search.setText(searchString);
        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = search.getText().toString();
                webView.loadUrl(URL + searchString);
                String fulljs = "javascript:(\n    function() { \n";
                fulljs += "        window.onload = function() {\n";
                fulljs += "            webviewScriptAPI.onLoad();\n";
                fulljs += "        };\n";
                fulljs += "    })()\n";
                webView.loadUrl(fulljs);
            }
        });

//        webView.loadUrl(URL + searchString);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        webView.addJavascriptInterface(new ObjectExtension(), "webviewScriptAPI");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview", "on page finished and url is " + url);

//                webView.loadUrl("javascript:(function() { " + spliceDomOperations() + "})()");
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
                Log.d("webview", "on newProgress changed " + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //titleview.setText(title);
                Log.d("webview", "on received title");
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("webview", "console message : " + consoleMessage.message() + " ; " + consoleMessage.lineNumber() + " ; " + consoleMessage.sourceId());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.loadUrl("https://www.google.com.hk/search?q=snb");
    }

    private String spliceDomOperations() {
        StringBuilder builder = new StringBuilder();
        for (String domId : DOM_IDS) {
            builder.append("document.getElementById('" + domId + "').style.display='none';");
        }

        builder.append("webviewScriptAPI.onLoad();");
        return builder.toString();
    }

    final class ObjectExtension {
        @JavascriptInterface
        public void onLoad() {
            Log.d("webview", "onLoadCompleted");
            Toast.makeText(WebViewActivity.this, "On Load Complete", Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.setVisibility(View.VISIBLE);
                }
            }, delay);
        }
    }
}
