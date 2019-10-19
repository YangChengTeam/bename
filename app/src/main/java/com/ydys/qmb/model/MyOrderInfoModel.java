package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.PayRequestParams;

/**
 * Created by admin on 2017/4/7.
 */

public interface MyOrderInfoModel<T> {
    void myOrderList(PayRequestParams payParams, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
