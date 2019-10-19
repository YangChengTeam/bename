package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.ScwgInfoRet;
import com.ydys.qmb.bean.WxbzInfoRet;
import com.ydys.qmb.model.ScwgInfoModelImp;
import com.ydys.qmb.model.WxbzInfoModelImp;
import com.ydys.qmb.view.ScwgInfoView;
import com.ydys.qmb.view.WxbzInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class WxbzInfoPresenterImp extends BasePresenterImp<WxbzInfoView, WxbzInfoRet> implements WxbzInfoPresenter {

    private Context context = null;
    private WxbzInfoModelImp wxbzInfoModelImp = null;

    public WxbzInfoPresenterImp(WxbzInfoView view, Context context) {
        super(view);
        this.context = context;
        this.wxbzInfoModelImp = new WxbzInfoModelImp(context);
    }

    @Override
    public void getWxbzInfo(String xing, String ming, int year, int month, int days, int hours) {
        wxbzInfoModelImp.getWxbzInfo(xing, ming, year, month, days, hours, this);
    }
}
