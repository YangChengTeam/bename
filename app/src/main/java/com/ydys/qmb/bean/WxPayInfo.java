package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

public class WxPayInfo {
    private String status;//状态
    private String orderno;//订单编号
    private String amount;//金额
    private WxParams params;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public WxParams getParams() {
        return params;
    }

    public void setParams(WxParams params) {
        this.params = params;
    }

    public class WxParams {
        private String appid;//微信开放平台审核通过的应用APPID
        private String mch_id;//微信支付分配的商户号
        private String prepayid;//微信返回的支付交易会话ID
        @SerializedName("package")
        private String payPackage;//Sign=WXPay",
        private String noncestr;//随机字符串
        private String timestamp;//当前时间戳
        private String sign;//签名

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPayPackage() {
            return payPackage;
        }

        public void setPayPackage(String payPackage) {
            this.payPackage = payPackage;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

}
