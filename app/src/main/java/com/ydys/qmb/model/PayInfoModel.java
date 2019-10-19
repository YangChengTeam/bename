package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.PayRequestParams;

/**
 * Created by admin on 2017/4/7.
 */

public interface PayInfoModel<T> {
    void createOrder(PayRequestParams payParams, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
