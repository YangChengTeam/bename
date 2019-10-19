package com.ydys.qmb.bean;

public class BuyVipRet extends ResultInfo {

    private BuyVipInfo data;

    public BuyVipInfo getData() {
        return data;
    }

    public void setData(BuyVipInfo data) {
        this.data = data;
    }

    public class BuyVipInfo {
        private String orderno;

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }
    }
}
