package com.example.administrator.bt_android_buoi15;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.administrator.bt_android_buoi15.web.WebClient;
import com.example.administrator.bt_android_buoi15.web.WebOffline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.administrator.bt_android_buoi15.R.menu.menu_seach;

public class MainActivity extends AppCompatActivity {
    private ListView lvTinTuc, lvDaLuu, lvYeuThich;
    private ArrayList<Item_news>arrayList =new ArrayList<>();
    private ArrayList<ItemDataWeb>arrayListData =new ArrayList<>();
    private ArrayList<ItemDataWeb1>arrayListDataYeuthich =new ArrayList<>();
    private SQLiteData sqLiteData;
    private Adapter_News adapter;
    private Adapter_News1 adapter_news;
    private Adapter_News2 adapter_news2;
    private ItemDataWeb itemDataWeb;
    private ItemDataWeb1 itemDataWeb1;
    private Dialog dialog, dialogYeuthich, dialogLuuTrangWeb, dialogXoa;
    int acc, acc1;
    private XML_Async xml_async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteData = new SQLiteData(this);
        readData();
        initTagHost();
        initView();
        initDialog();

        addEvent();
    }


    private void addEvent() {
        lvTinTuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item_news item_news = arrayList.get(position);
                Intent intent = new Intent(MainActivity.this, WebClient.class);
                intent.putExtra("link",item_news.getLink());
                startActivity(intent);
            }
        });

        lvTinTuc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                acc1 = position;
                initDialogLuuTrangWeb();
                dialogLuuTrangWeb.show();
                return false;
            }
        });


        lvDaLuu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDataWeb itemDataWeb = arrayListData.get(position);
                Intent intent = new Intent(MainActivity.this, WebOffline.class);
                intent.putExtra("link",itemDataWeb.getTitle());
                startActivity(intent);
            }
        });
        lvDaLuu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                acc = position+1;
                acc1 = position;
                initDialogYeuthich();
                dialogYeuthich.show();
                return false;
            }
        });

        lvYeuThich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDataWeb1 itemDataWeb1 = arrayListDataYeuthich.get(position);
                Intent intent = new Intent(MainActivity.this, WebClient.class);
                intent.putExtra("link",itemDataWeb1.getLink());
                startActivity(intent);
            }
        });
        lvYeuThich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                acc1 =position;
                initDialogXoa();
                dialogXoa.show();
                return false;
            }
        });
    }
    private void initDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void initDialogXoa() {
        dialogXoa = new Dialog(this);
        dialogXoa.setContentView(R.layout.dialog_xoa);
        dialogXoa.setCanceledOnTouchOutside(false);
        dialogXoa.setCancelable(false);
        Button btnXoa = (Button) dialogXoa.findViewById(R.id.btnXoa);
        Button btnHuy = (Button) dialogXoa.findViewById(R.id.btnHuyXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListDataYeuthich.remove(acc1);
                adapter_news2.notifyDataSetChanged();
                dialogXoa.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogXoa.dismiss();
            }
        });
    }

    private void initDialogLuuTrangWeb() {
        dialogLuuTrangWeb = new Dialog(this);
        dialogLuuTrangWeb.setContentView(R.layout.dialogluu);
        dialogLuuTrangWeb.setCanceledOnTouchOutside(false);
        dialogLuuTrangWeb.setCancelable(false);
        Button btnLuuluu = (Button) dialogLuuTrangWeb.findViewById(R.id.btnLuuluu);
        Button btnHuyLuu = (Button) dialogLuuTrangWeb.findViewById(R.id.btnHuyLuu);
        btnLuuluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListData.clear();
                Item_news item_news = arrayList.get(acc1);
                String title = item_news.getTitle();
                String desc = item_news.getDesc();
                String pubdate = item_news.getPubDate();
                String img = item_news.getImg();
                String link = item_news.getLink()+"";
                String content="";
                itemDataWeb = new ItemDataWeb(title,desc,pubdate,img,link,content);
                sqLiteData.insert(itemDataWeb);
                readData();
                adapter_news.notifyDataSetChanged();
                File path = Environment.getExternalStorageDirectory();
                DownloadTask downloadTask = new DownloadTask(MainActivity.this,path,"Download","web.html");
                downloadTask.execute(link);
                dialogLuuTrangWeb.dismiss();
            }
        });

        btnHuyLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLuuTrangWeb.dismiss();
            }
        });
    }

    private void initDialogYeuthich() {
        dialogYeuthich = new Dialog(this);
        dialogYeuthich.setContentView(R.layout.dialog_daluu);
        dialogYeuthich.setCanceledOnTouchOutside(true);
        dialogYeuthich.setCancelable(true);
        Button btnYeuthich = (Button) dialogYeuthich.findViewById(R.id.btnYeuThich);
        Button btnXoa = (Button) dialogYeuthich.findViewById(R.id.btnXoa);
        btnYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDataWeb itemDataWeb = arrayListData.get(acc1);
                String title = itemDataWeb.getTitle();
                String desc = itemDataWeb.getDesc();
                String pubdate = itemDataWeb.getPubdate();
                String img = itemDataWeb.getImg();
                String link = itemDataWeb.getLink()+"";
                String content="";
                itemDataWeb1 = new ItemDataWeb1(title,desc,pubdate,img,link,content);
                arrayListDataYeuthich.add(itemDataWeb1);
                adapter_news2.notifyDataSetChanged();
                dialogYeuthich.dismiss();

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteData.delete(acc);
                readData();
                adapter_news.notifyDataSetChanged();
                dialogYeuthich.dismiss();
            }
        });
    }

    private void initTagHost() {
        TabHost tabHost = (TabHost) findViewById(R.id.tagHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 =tabHost.newTabSpec("T1");
        tabSpec1.setIndicator("Tin tức");
        //tabSpec1.setIndicator("",getResources().getDrawable(R.drawable.location_news));
        tabSpec1.setContent(R.id.tab1);
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 =tabHost.newTabSpec("T2");
        tabSpec2.setIndicator("Đã lưu");
        tabSpec2.setContent(R.id.tab2);
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 =tabHost.newTabSpec("T3");
        tabSpec3.setIndicator("Yêu thích");
        tabSpec3.setContent(R.id.tab3);
        tabHost.addTab(tabSpec3);
    }

    private void initView() {
        lvTinTuc = (ListView) findViewById(R.id.lvTinTuc);
        lvDaLuu = (ListView) findViewById(R.id.lvDaLuu);
        lvYeuThich = (ListView) findViewById(R.id.lvYeuThich);
        arrayList = new ArrayList<>();
       adapter = new Adapter_News(MainActivity.this,arrayList);
        adapter_news = new Adapter_News1(MainActivity.this,arrayListData);
        adapter_news2 = new Adapter_News2(MainActivity.this,arrayListDataYeuthich);
        lvDaLuu.setAdapter(adapter_news);
        lvTinTuc.setAdapter(adapter);
        lvYeuThich.setAdapter(adapter_news2);
        xml_async =new XML_Async(MainActivity.this,handler);
        xml_async.execute("https://news.google.com/news/rss/search/section/q/"
                + Uri.encode("bongda") + "?hl=vi&ned=vi_vn");
        adapter.notifyDataSetChanged();
    }

    private void readData() {
        arrayListData.clear();
        arrayListData.addAll(sqLiteData.getData());
    }

    private void initNavirationOpen() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void initNavirationClose() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_seach,menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initNavirationOpen();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                initNavirationClose();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                xml_async = new XML_Async(MainActivity.this,handler);
                if (xml_async.isCancelled()==true){
                    arrayList.clear();
                    xml_async.execute("https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=hoahau");
                    adapter.notifyDataSetChanged();
                }else {
                    String s =query;
                    s= s.trim();
                    String []arr= s.split(" ");
                    String s1= "";
                    for (String tu :arr){
                        if (tu.trim().length()!=0){
                            s1+=tu+" ";
                        }
                        s1=s1.trim();
                    }
                    arrayList.clear();
                    xml_async.execute("https://news.google.com/news/rss/search/section/q/"
                            + Uri.encode(s1) + "?hl=vi&ned=vi_vn");
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==XML_Async.WHAT){
                arrayList.clear();
                arrayList.addAll((Collection<? extends Item_news>) msg.obj);
                adapter.notifyDataSetChanged();
            }
        }
    };


    public static class DownloadTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog mPDialog;
        private Context mContext;
        private String link;
        private PowerManager.WakeLock mWakeLock;
        private File mTargetFile;
        public DownloadTask(Context context,File targetFile,String dialogMessage,String link) {
            this.mContext = context;
            this.mTargetFile = targetFile;
            this.link = link;
            mPDialog = new ProgressDialog(context);
            mPDialog.setMessage(dialogMessage);
            mPDialog.setIndeterminate(true);
            mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mPDialog.setCancelable(true);
            final DownloadTask me = this;
            mPDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    me.cancel(true);
                }
            });
            Log.i("DownloadTask","Constructor done");
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                Log.i("DownloadTask","Response " + connection.getResponseCode());
                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                output = new FileOutputStream(mTargetFile+"/"+link,false);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        Log.i("DownloadTask","Cancelled");
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();

            mPDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mPDialog.setIndeterminate(false);
            mPDialog.setMax(100);
            mPDialog.setProgress(progress[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("DownloadTask", "Work Done! PostExecute");
            mWakeLock.release();
            mPDialog.dismiss();
            if (result != null)
                Toast.makeText(mContext,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(mContext,"File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }

}
