package com.example.administrator.bt_android_buoi15;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 9/25/2017.
 */

public class XML_Async extends AsyncTask<String,Void,ArrayList<Item_news>> {
    public static final int WHAT =1;
    private Handler handler;
    private Context context;
    private Dialog dialog;

    public XML_Async(Context context,Handler handler) {
        this.context= context;
        this.handler = handler;
        initDialog();
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Item_news> doInBackground(String... params) {
        try {
            String url = params[0];
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            HandlerItem handlerItem = new HandlerItem();
            parser.parse(url,handlerItem);
            return handlerItem.getArrNews();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Item_news> item_newses) {
        super.onPostExecute(item_newses);
        Message message = new Message();
        message.what = WHAT;
        dialog.dismiss();
        message.obj = item_newses;
        handler.sendMessage(message);
    }


    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
}
