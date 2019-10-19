package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.LoginParams;

/**
 * Created by admin on 2017/4/7.
 */

public interface UserInfoModel<T> {
    void login(LoginParams loginParams,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
