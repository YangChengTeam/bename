package com.ydys.qmb.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class HotWordRet extends ResultInfo {

    private List<HotWordInfo> data;

    private List<HotGifCollection> collections;

    public List<HotWordInfo> getData() {
        return data;
    }

    public void setData(List<HotWordInfo> data) {
        this.data = data;
    }

    public List<HotGifCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<HotGifCollection> collections) {
        this.collections = collections;
    }
}
