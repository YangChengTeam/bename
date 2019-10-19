package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.model.ChongMingInfoModelImp;
import com.ydys.qmb.model.PoetryInfoModelImp;
import com.ydys.qmb.view.ChongMingInfoView;
import com.ydys.qmb.view.PoetryInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class ChongMingInfoPresenterImp extends BasePresenterImp<ChongMingInfoView, ChongMingInfoRet> implements ChongMingInfoPresenter {

    private Context context = null;
    private ChongMingInfoModelImp chongMingInfoModelImp = null;

    public ChongMingInfoPresenterImp(ChongMingInfoView view, Context context) {
        super(view);
        this.context = context;
        this.chongMingInfoModelImp = new ChongMingInfoModelImp(context);
    }

    @Override
    public void getChongMingInfo(String nameId) {
        chongMingInfoModelImp.getChongMingInfo(nameId, this);
    }
}
