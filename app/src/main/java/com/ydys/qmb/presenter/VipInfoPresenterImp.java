package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.PriceInfoRet;
import com.ydys.qmb.model.VipInfoModelImp;
import com.ydys.qmb.view.PriceInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class VipInfoPresenterImp extends BasePresenterImp<IBaseView, PriceInfoRet> implements VipInfoPresenter {

    private Context context = null;
    private VipInfoModelImp vipInfoModelImp = null;

    public VipInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.vipInfoModelImp = new VipInfoModelImp(context);
    }

    @Override
    public void vipPrice() {
        vipInfoModelImp.vipPrice(this);
    }
}
