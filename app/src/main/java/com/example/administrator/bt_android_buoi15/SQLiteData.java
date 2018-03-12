package com.example.administrator.bt_android_buoi15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 10/4/2017.
 */

public class SQLiteData {
    public static final String PATH =
            Environment.getDataDirectory().getPath()
                    + "/data/com.example.administrator.bt_android_buoi15/database/";

    public static final String DB_NAME = "weboffline.sqlite";
    public static final String TABLE_NAME = "webdata";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String PUBDATE = "pubdate";
    public static final String IMG = "img";
    public static final String LINK = "link";
    public static final String CONTENT = "content";

    private Context context;
    private SQLiteDatabase database;

    public SQLiteData(Context context) {
        this.context = context;
        copyDatabaseToProject();
    }

    private void copyDatabaseToProject() {
        try {
            File file = new File(PATH + DB_NAME);
            if (file.exists()) {
                return;
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = context.getAssets().open(DB_NAME);
            byte[] b = new byte[1024];
            int count = inputStream.read(b);
            while (count!=-1){
                outputStream.write(b,0,count);
                count = inputStream.read(b);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void openDatabase(){
        database = context.openOrCreateDatabase(PATH + DB_NAME,Context.MODE_PRIVATE,null);
    }

    public void closeDatabase(){
        database.close();
    }
    public ArrayList<ItemDataWeb> getData(){
        ArrayList<ItemDataWeb> arr = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
       // int indexId =  cursor.getColumnIndex(ID);
        int indexTitle =  cursor.getColumnIndex(TITLE);
        int indexDesc =  cursor.getColumnIndex(DESC);
        int indexPubdate =  cursor.getColumnIndex(PUBDATE);
        int indexImg =  cursor.getColumnIndex(IMG);
        int indexLink =  cursor.getColumnIndex(LINK);
        int indexContent =  cursor.getColumnIndex(CONTENT);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            //int id = cursor.getInt(indexId);
            String title = cursor.getString(indexTitle);
            String desc = cursor.getString(indexDesc);
            String pubdate = cursor.getString(indexPubdate);
            String img = cursor.getString(indexImg);
            String link = cursor.getString(indexLink);
            String content = cursor.getString(indexContent);
            ItemDataWeb itemDataWeb = new ItemDataWeb(title,desc,pubdate,img,link,content);
            arr.add(itemDataWeb);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }

    public long insert(ItemDataWeb itemDataWeb){
        ContentValues values = new ContentValues();
        values.put(TITLE,itemDataWeb.getTitle());
        values.put(DESC,itemDataWeb.getDesc());
        values.put(PUBDATE,itemDataWeb.getPubdate());
        values.put(IMG,itemDataWeb.getImg());
        values.put(LINK,itemDataWeb.getLink());
        values.put(CONTENT,itemDataWeb.getContent());
        openDatabase();
        long id = database.insert(TABLE_NAME, null, values);
        closeDatabase();
        return id;
    }

    public int delete(int id){
        String[] args = {id+""};
        openDatabase();
        int rows = database.delete(TABLE_NAME,ID+"=?",args);
        closeDatabase();
        return rows;
    }
}
