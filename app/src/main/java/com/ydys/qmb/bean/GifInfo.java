package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2019/4/9.
 */
public class GifInfo {

    private String id;

    @SerializedName("img_name")
    private String imageName;

    @SerializedName("img_url")
    private String gifUrl;

    @SerializedName("real_img_url")
    private String realImgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getRealImgUrl() {
        return realImgUrl;
    }

    public void setRealImgUrl(String realImgUrl) {
        this.realImgUrl = realImgUrl;
    }
}
