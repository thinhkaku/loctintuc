package com.example.administrator.bt_android_buoi15;

import java.io.Serializable;

/**
 * Created by Administrator on 10/4/2017.
 */

public class ItemDataWeb implements Serializable {
    private  int id;
    private String title;
    private String desc;
    private String pubdate;
    private String img;
    private String link;
    private String content;

    public ItemDataWeb(String title, String desc, String pubdate, String img, String link,String content) {
        this.title = title;
        this.desc = desc;
        this.pubdate = pubdate;
        this.img = img;
        this.link = link;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ItemDataWeb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
