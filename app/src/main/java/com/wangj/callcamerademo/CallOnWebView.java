package com.wangj.callcamerademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CallOnWebView extends AppCompatActivity {
    private String targetUrl = "file:///android_asset/test.html";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_on_web_view);
        setTitle("通过WebView调用相机");

        webView = (WebView) findViewById(R.id.webView);

        initView();
    }

    private void initView() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(url);
            }
        });

        webView.loadUrl(targetUrl);
    }
}
