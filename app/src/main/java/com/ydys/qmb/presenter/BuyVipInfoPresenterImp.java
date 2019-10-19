package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.BuyVipRet;
import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.model.BuyVipInfoModelImp;
import com.ydys.qmb.model.ChongMingInfoModelImp;
import com.ydys.qmb.view.ChongMingInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class BuyVipInfoPresenterImp extends BasePresenterImp<IBaseView, BuyVipRet> implements BuyVipInfoPresenter {

    private Context context = null;
    private BuyVipInfoModelImp buyVipInfoModelImp = null;

    public BuyVipInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.buyVipInfoModelImp = new BuyVipInfoModelImp(context);
    }

    @Override
    public void isBuyVip(String userId, String surname) {
        buyVipInfoModelImp.isBuyVip(userId, surname, this);
    }
}
