package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.PayInfoRet;
import com.ydys.qmb.bean.PayRequestParams;
import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.model.PayInfoModelImp;
import com.ydys.qmb.model.PoetryInfoModelImp;
import com.ydys.qmb.view.PoetryInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class PayInfoPresenterImp extends BasePresenterImp<IBaseView, PayInfoRet> implements PayInfoPresenter {

    private Context context = null;
    private PayInfoModelImp payInfoModelImp = null;

    public PayInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.payInfoModelImp = new PayInfoModelImp(context);
    }

    @Override
    public void createOrder(PayRequestParams payParams) {
        payInfoModelImp.createOrder(payParams, this);
    }
}
