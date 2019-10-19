package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface NameDetailModel<T> {
    void getNameDetail(String xing, String ming, String userId,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
