package com.ydys.qmb.bean;

public class PayRequestParams {

    private String user_id;//用户ID(必填)
    private int type;//登录类型(1:手机 2:微信 3：QQ) 必填
    private String phone;//手机号  (手机用户则传递此参数)(必填)
    private String openid;//用户登录的openid  (QQ和微信用户则传递此参数)(必填)

    private String pay_way;//支付方式(alipay:支付宝支付，wxpay：微信支付)必填
    private String amount;//支付金额  必填
    private String xing;//解锁的姓氏 必填
    private String title;//订单名称 必填
    private int page;
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

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
