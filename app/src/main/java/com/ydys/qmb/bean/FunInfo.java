package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by myflying on 2019/4/9.
 */
public class FunInfo implements Serializable {

    private String id;

    @SerializedName("gif_name")
    private String name;

    @SerializedName("gif_cover")
    private String coverImage;

    @SerializedName("gif_url")
    private String gifUrl;

    @SerializedName("gif_mp4")
    private String gifMp4;

    @SerializedName("gif_real_url")
    private String gifRealUrl;

    @SerializedName("gif_real_mp4")
    private String gifRealMp4;

    @SerializedName("is_show")
    private int isShow;

    private int width;

    private int height;

    private String subtype;

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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getGifMp4() {
        return gifMp4;
    }

    public void setGifMp4(String gifMp4) {
        this.gifMp4 = gifMp4;
    }

    public String getGifRealUrl() {
        return gifRealUrl;
    }

    public void setGifRealUrl(String gifRealUrl) {
        this.gifRealUrl = gifRealUrl;
    }

    public String getGifRealMp4() {
        return gifRealMp4;
    }

    public void setGifRealMp4(String gifRealMp4) {
        this.gifRealMp4 = gifRealMp4;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
