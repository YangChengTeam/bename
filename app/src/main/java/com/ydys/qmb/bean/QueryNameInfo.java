package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class QueryNameInfo {
    private String user_id;//用户ID(已登录则必填)
    private int type;//登录类型(1:手机 2:微信 3：QQ) 已登录则必填
    private String phone;//手机号  (手机用户则传递此参数)(已登录则必填)
    private String openid;//用户登录的openid  (QQ和微信用户则传递此参数)(已登录则必填)
    private String xing;//需要查的姓氏(必填,最多两个汉字)
    private int single;//名字是否单名(1：单名(一个汉字) 2：复名(两个汉字)   必填)
    private int page;//页码（默认第一页）
    private int sex;//(0:女 1：男)

    private String nameId;

    private double lastNameScore;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public double getLastNameScore() {
        return lastNameScore;
    }

    public void setLastNameScore(double lastNameScore) {
        this.lastNameScore = lastNameScore;
    }
}
