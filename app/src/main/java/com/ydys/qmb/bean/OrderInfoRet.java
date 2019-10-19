package com.ydys.qmb.bean;

import java.util.List;

public class OrderInfoRet extends ResultInfo {

    private List<OrderInfo> data;

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }
}
