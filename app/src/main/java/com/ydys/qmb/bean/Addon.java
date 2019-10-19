package com.ydys.qmb.bean;

import android.graphics.Bitmap;

/**
 * 本地简单使用,实际项目中与贴纸相关的属性可以添加到此类中
 */
public class Addon {
    private int    id;
    private Bitmap bitmap;
    //JSON用到
    public Addon() {

    }

    public Addon(int id) {
        this.id = id;
    }

    public Addon(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
