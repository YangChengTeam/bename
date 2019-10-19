package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.QueryNameInfo;

/**
 * Created by admin on 2017/4/7.
 */

public interface AddKeepInfoModel<T> {
    void addKeep(QueryNameInfo queryNameInfo, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
