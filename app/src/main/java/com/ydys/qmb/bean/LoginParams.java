package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

public class LoginParams {
    private int type;//登录类型(1:手机 2:微信 3：QQ) 必填
    private String siteId;//来源站点ID(包信息有site_id时使用此参数)
    private String agent;//来源渠道 必填
    private String version;//版本
    private String imei;//手机串号 必填

    private String phone;//手机号  (如果是手机用户则传递此参数)
    private String face;//头像  (如果是QQ和微信用户则传递此参数)
    @SerializedName("nick_name")
    private String nickname;//用户昵称  (如果是QQ和微信用户则传递此参数)
    private String openid;//用户登录的openid  (如果是QQ和微信用户则传递此参数)

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
