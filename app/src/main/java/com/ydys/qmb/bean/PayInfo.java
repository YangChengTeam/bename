package com.ydys.qmb.bean;

public class PayInfo {
    private WxPayInfo wx_orderinfo;
    private String ali_orderinfo;

    public WxPayInfo getWx_orderinfo() {
        return wx_orderinfo;
    }

    public void setWx_orderinfo(WxPayInfo wx_orderinfo) {
        this.wx_orderinfo = wx_orderinfo;
    }

    public String getAli_orderinfo() {
        return ali_orderinfo;
    }

    public void setAli_orderinfo(String ali_orderinfo) {
        this.ali_orderinfo = ali_orderinfo;
    }
}
