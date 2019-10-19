package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface ChongMingInfoModel<T> {
    void getChongMingInfo(String nameId,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
