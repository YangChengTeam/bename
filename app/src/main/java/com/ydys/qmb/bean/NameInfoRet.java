package com.ydys.qmb.bean;

import java.util.List;

public class NameInfoRet extends ResultInfo {

    private List<NameInfo> data;

    public List<NameInfo> getData() {
        return data;
    }

    public void setData(List<NameInfo> data) {
        this.data = data;
    }
}
