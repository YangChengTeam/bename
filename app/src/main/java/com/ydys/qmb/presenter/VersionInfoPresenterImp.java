package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.VersionInfoRet;
import com.ydys.qmb.model.VersionInfoModelImp;
import com.ydys.qmb.view.VersionInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class VersionInfoPresenterImp extends BasePresenterImp<IBaseView, VersionInfoRet> implements VersionInfoPresenter {

    private Context context = null;
    private VersionInfoModelImp versionInfoModelImp = null;

    public VersionInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.versionInfoModelImp = new VersionInfoModelImp(context);
    }

    @Override
    public void getNewVersion(String channel) {
        versionInfoModelImp.getNewVersion(channel,this);
    }
}
