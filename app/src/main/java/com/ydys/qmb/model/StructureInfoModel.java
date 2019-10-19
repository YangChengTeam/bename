package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface StructureInfoModel<T> {
    void getStructureInfo(String nameId, String xing, String ming, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
