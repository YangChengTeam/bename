package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.model.PoetryInfoModelImp;
import com.ydys.qmb.view.PoetryInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class PoetryInfoPresenterImp extends BasePresenterImp<PoetryInfoView, PoetryInfoRet> implements PoetryInfoPresenter {

    private Context context = null;
    private PoetryInfoModelImp poetryInfoModelImp = null;

    public PoetryInfoPresenterImp(PoetryInfoView view, Context context) {
        super(view);
        this.context = context;
        this.poetryInfoModelImp = new PoetryInfoModelImp(context);
    }

    @Override
    public void getPoetryInfo(String nameId) {
        poetryInfoModelImp.getPoetryInfo(nameId, this);
    }
}
