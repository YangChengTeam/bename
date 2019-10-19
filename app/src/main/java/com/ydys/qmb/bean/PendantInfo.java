package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangdinghui on 2019/4/25.
 */
public class PendantInfo {
    private int id;

    @SerializedName("pen_type")
    private int type;

    @SerializedName("pen_img_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
