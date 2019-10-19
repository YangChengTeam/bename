package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2019/4/10.
 */
public class HeadInfo {
    private String id;

    @SerializedName("head_url")
    private String headUrl;

    @SerializedName("real_url")
    private String realUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }
}
