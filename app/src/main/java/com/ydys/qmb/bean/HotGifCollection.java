package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangdinghui on 2019/4/23.
 */
public class HotGifCollection {
    private int id;

    @SerializedName("category_name")
    private String collectionName;

    private String cover;

    @SerializedName("is_hot")
    private int isHot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }
}
