package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.QueryNameInfo;

/**
 * Created by admin on 2017/4/7.
 */

public interface NameInfoModel<T> {
    void getNameList(QueryNameInfo queryNameInfo, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void getCollectionList(QueryNameInfo queryNameInfo, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
