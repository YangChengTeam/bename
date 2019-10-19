package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface VersionInfoModel<T> {
    void getNewVersion(String channel,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
