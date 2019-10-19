package com.ydys.qmb.bean;

/**
 * Created by myflying on 2019/4/15.
 */
public class StickerInfo {

    private String id;

    private String name;

    private String stickerUrl;

    public StickerInfo(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStickerUrl() {
        return stickerUrl;
    }

    public void setStickerUrl(String stickerUrl) {
        this.stickerUrl = stickerUrl;
    }
}
