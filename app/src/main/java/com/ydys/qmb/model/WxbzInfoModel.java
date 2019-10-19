package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface WxbzInfoModel<T> {
    void getWxbzInfo(String xing, String ming, int year, int month, int days, int hours, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
