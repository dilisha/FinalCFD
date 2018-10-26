package com.example.resourcetrack1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class contribute1 extends AppCompatActivity {

    private WebView webContribute;
    Activity activity;
    private ProgressDialog progDialog;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute1);

        activity = this;

        progDialog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDialog.setCancelable(false);

        webContribute = (WebView) findViewById(R.id.webview);
/*
       */
        webContribute.getSettings().setJavaScriptEnabled(true);
        webContribute.getSettings().setLoadWithOverviewMode(true);
        webContribute.getSettings().setUseWideViewPort(true);

        //to put load website within App

        webContribute.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDialog.show();
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDialog.dismiss();
            }

            //to eliminate error
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

                handler.proceed();
                error.getCertificate();
            }

        });


        /// to make it load faster

        webContribute.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            webContribute.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webContribute.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webContribute.loadUrl("https://pmnrf.gov.in/");

    }
    @Override
    public void onBackPressed() {
        if(webContribute.canGoBack())
            webContribute.goBack();
        else
        super.onBackPressed();
    }


}
