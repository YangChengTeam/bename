package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.bean.PopularInfoRet;
import com.ydys.qmb.model.PoetryInfoModelImp;
import com.ydys.qmb.model.PopularInfoModelImp;
import com.ydys.qmb.view.PoetryInfoView;
import com.ydys.qmb.view.PopularInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class PopularInfoPresenterImp extends BasePresenterImp<PopularInfoView, PopularInfoRet> implements PopularInfoPresenter {

    private Context context = null;
    private PopularInfoModelImp popularInfoModelImp = null;

    public PopularInfoPresenterImp(PopularInfoView view, Context context) {
        super(view);
        this.context = context;
        this.popularInfoModelImp = new PopularInfoModelImp(context);
    }

    @Override
    public void getPopularInfo(String nameId) {
        popularInfoModelImp.getPopularInfo(nameId, this);
    }

}
