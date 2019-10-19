package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.OrderInfoRet;
import com.ydys.qmb.bean.PayRequestParams;
import com.ydys.qmb.model.MyOrderInfoModelImp;
import com.ydys.qmb.view.OrderInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class MyOrderInfoPresenterImp extends BasePresenterImp<OrderInfoView, OrderInfoRet> implements MyOrderInfoPresenter {

    private Context context = null;
    private MyOrderInfoModelImp myOrderInfoModelImp = null;

    public MyOrderInfoPresenterImp(OrderInfoView view, Context context) {
        super(view);
        this.context = context;
        this.myOrderInfoModelImp = new MyOrderInfoModelImp(context);
    }

    @Override
    public void myOrderList(PayRequestParams payParams) {
        myOrderInfoModelImp.myOrderList(payParams, this);
    }
}
