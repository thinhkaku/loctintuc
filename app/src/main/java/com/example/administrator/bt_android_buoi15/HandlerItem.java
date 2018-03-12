package com.example.administrator.bt_android_buoi15;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 9/25/2017.
 */

public class HandlerItem extends DefaultHandler {
    private static final String TITLE ="title";
    private static final String LINK ="link";
    private static final String PUBDATE ="pubDate";
    private static final String DESC ="description";
    private static final String ITEM ="item";

    private Item_news item_news;
    private ArrayList<Item_news>arrayList = new ArrayList<>();
    private StringBuilder builder;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName ==ITEM){
            item_news = new Item_news();
        }
        builder = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (item_news == null){
            return;
        }
        switch (qName){

            case TITLE:
                item_news.setTitle(builder.toString());
                break;

            case DESC:
                String img = "";
                String desc = "";
                try {
                    int startDesc = builder.indexOf("target=\"_blank\">")+16;
                    int endDesc = builder.indexOf("</a>");
                    //int index = builder.indexOf("src='")+5;
                    //img = builder.substring(index,builder.indexOf("'",index+1));
                    //index = builder.lastIndexOf("<br </")+6;
                    desc =builder.substring(startDesc,endDesc);
                    desc.trim();
                    int startLink = builder.indexOf("src=")+5;
                    int endLink = builder.indexOf("border=\"1\"")-2;
                    if(startLink < 0 || endLink<0) {
                        img = "notImage";

                    }else{
                        img = builder.substring(startLink, endLink);
                        img.trim();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                item_news.setImg(img);
                item_news.setDesc(desc);
                break;
            case PUBDATE:
                item_news.setPubDate(builder.toString());
                break;


            case ITEM:
                arrayList.add(item_news);
                break;


            case LINK:
                item_news.setLink(builder.toString());
                break;
        }
    }

    public ArrayList<Item_news> getArrNews() {
        return arrayList;
    }
}
