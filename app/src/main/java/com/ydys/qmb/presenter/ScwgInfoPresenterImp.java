package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.ScwgInfoRet;
import com.ydys.qmb.model.ScwgInfoModelImp;
import com.ydys.qmb.view.ScwgInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class ScwgInfoPresenterImp extends BasePresenterImp<ScwgInfoView, ScwgInfoRet> implements ScwgInfoPresenter {

    private Context context = null;
    private ScwgInfoModelImp scwgInfoModelImp = null;

    public ScwgInfoPresenterImp(ScwgInfoView view, Context context) {
        super(view);
        this.context = context;
        this.scwgInfoModelImp = new ScwgInfoModelImp(context);
    }

    @Override
    public void getScwgInfo(String nameId, String xing, String ming) {
        scwgInfoModelImp.getScwgInfo(nameId, xing, ming, this);
    }
}
