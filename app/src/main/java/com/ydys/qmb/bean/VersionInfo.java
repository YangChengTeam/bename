package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangdinghui on 2019/4/25.
 */
public class VersionInfo {
    private int id;

    @SerializedName("app_name")
    private String appName;

    @SerializedName("version_name")
    private String versionName;

    @SerializedName("version_code")
    private int versionCode;

    @SerializedName("version_date")
    private String versionDate;

    @SerializedName("version_url")
    private String updateUrl;

    @SerializedName("version_desc")
    private String updateContent;

    @SerializedName("version_is_change")
    private int isForce;//版本是否强制更新  0:不强制 1：强制

    private String kfemail;//客服邮箱

    private String kfway;//客服联系方式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public String getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }

    public String getKfemail() {
        return kfemail;
    }

    public void setKfemail(String kfemail) {
        this.kfemail = kfemail;
    }

    public String getKfway() {
        return kfway;
    }

    public void setKfway(String kfway) {
        this.kfway = kfway;
    }
}
