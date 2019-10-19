package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.model.NameDetailModelImp;
import com.ydys.qmb.model.NameInfoModelImp;
import com.ydys.qmb.view.NameDetailView;

/**
 * Created by admin on 2017/4/7.
 */

public class NameDetailPresenterImp extends BasePresenterImp<NameDetailView, NameDetailRet> implements NameDetailPresenter {

    private Context context = null;
    private NameDetailModelImp nameDetailModelImp = null;

    public NameDetailPresenterImp(NameDetailView view, Context context) {
        super(view);
        this.context = context;
        this.nameDetailModelImp = new NameDetailModelImp(context);
    }

    @Override
    public void getNameDetail(String xing, String ming,String userId) {
        nameDetailModelImp.getNameDetail(xing, ming, userId,this);
    }
}
