package com.example.administrator.bt_android_buoi15.web;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.bt_android_buoi15.R;

/**
 * Created by Administrator on 10/1/2017.
 */

public class MyWebvewClient extends WebViewClient {
    private Dialog dialog;
    private Context context;
    public MyWebvewClient(Context context) {
        this.context= context;
        initDialog();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (dialog.isShowing()==false){
            dialog.show();
        }else {
            dialog.dismiss();
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {

        dialog.dismiss();
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);

    }
    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

}
