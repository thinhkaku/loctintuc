package com.example.administrator.bt_android_buoi15;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 9/25/2017.
 */

public class Adapter_News extends ArrayAdapter<Item_news> {
    private LayoutInflater inflater;
    private ArrayList<Item_news>arrNews;
    private HolderNews holderNews;
    public Adapter_News(@NonNull Context context, @NonNull ArrayList<Item_news> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        arrNews = objects;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            holderNews = new HolderNews();
            convertView = inflater.inflate(R.layout.item,parent,false);
            holderNews.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holderNews.txtDesc = (TextView) convertView.findViewById(R.id.txtDesc);
            holderNews.txtPubDate = (TextView) convertView.findViewById(R.id.txtPubDate);
            holderNews.imgHinh = (ImageView) convertView.findViewById(R.id.imgHinh);
            convertView.setTag(holderNews);
        }else {
            holderNews = (HolderNews) convertView.getTag();
        }
        Item_news item_news = arrNews.get(position);
        holderNews.txtTitle.setText(item_news.getTitle());
        holderNews.txtDesc.setText(item_news.getDesc());
        holderNews.txtPubDate.setText(item_news.getPubDate());

        Glide.with(getContext()).load(item_news.getImg()).placeholder(R.drawable.icon_load).into(holderNews.imgHinh);
        return convertView;
    }

    private class HolderNews{
        private TextView txtTitle, txtPubDate, txtDesc;
        private ImageView imgHinh;
    }
}
