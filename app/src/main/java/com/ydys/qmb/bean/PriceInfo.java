package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PriceInfo {
    private double price;//原价

    @SerializedName("m_price")
    private double mPrice;//折后价

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
