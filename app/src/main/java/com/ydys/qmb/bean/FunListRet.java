package com.ydys.qmb.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class FunListRet extends ResultInfo {

    private List<FunInfo> data;

    public List<FunInfo> getData() {
        return data;
    }

    public void setData(List<FunInfo> data) {
        this.data = data;
    }
}
