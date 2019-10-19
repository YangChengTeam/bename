package com.ydys.qmb.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class StickerRet extends ResultInfo {

    private List<PendantInfo> data;

    public List<PendantInfo> getData() {
        return data;
    }

    public void setData(List<PendantInfo> data) {
        this.data = data;
    }
}
