package com.example.administrator.bt_android_buoi15.web;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.administrator.bt_android_buoi15.MainActivity;
import com.example.administrator.bt_android_buoi15.R;

/**
 * Created by Administrator on 10/3/2017.
 */

public class WebOffline extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webclientoffline);
        initView();
    }
    private void initView() {
        webView = (WebView) findViewById(R.id.webView1);
        goUrl();
        webView.setWebViewClient(new MyWebvewClient(WebOffline.this));


    }
    private void goUrl()  {
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        String path = ("file:///"+ Environment.getExternalStorageDirectory()+"/web.html");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(path);
    }

    private void showStaticContent()  {
        String staticContent="<h2>Select web page</h2>"
                + "<ul><li><a href='http://eclipse.org'>Eclipse</a></li>"
                +"<li><a href='http://google.com'>Google</a></li></ul>";
        webView.loadData(staticContent, "text/html", "UTF-8");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebOffline.this, MainActivity.class);
        startActivity(intent);
        finish();
        // super.onBackPressed();
    }
}
