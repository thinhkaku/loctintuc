package com.example.administrator.bt_android_buoi15.web;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.bt_android_buoi15.MainActivity;
import com.example.administrator.bt_android_buoi15.R;

import java.io.File;

/**
 * Created by Administrator on 10/1/2017.
 */

public class WebClient extends AppCompatActivity {
    private WebView webView;
    private String url;
    private MyWebvewClient myWebvewClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webclient);
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        goUrl();
        webView.setWebViewClient(new MyWebvewClient(WebClient.this));


    }
    private void goUrl()  {
        Intent intent = getIntent();
        url = intent.getStringExtra("link").trim();
        //String path = ("file:///"+Environment.getExternalStorageDirectory()+"/index.html");
        if(url.isEmpty())  {
            Toast.makeText(this,"Please enter url",Toast.LENGTH_SHORT).show();
            return;
        }
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private void showStaticContent()  {
        String staticContent="<h2>Select web page</h2>"
                + "<ul><li><a href='http://eclipse.org'>Eclipse</a></li>"
                +"<li><a href='http://google.com'>Google</a></li></ul>";
        webView.loadData(staticContent, "text/html", "UTF-8");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebClient.this, MainActivity.class);
        startActivity(intent);
        finish();
       // super.onBackPressed();
    }
}
