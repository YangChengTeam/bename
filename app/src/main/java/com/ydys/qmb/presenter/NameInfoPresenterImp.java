package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.model.NameInfoModelImp;

/**
 * Created by admin on 2017/4/7.
 */

public class NameInfoPresenterImp extends BasePresenterImp<IBaseView, NameInfoRet> implements NameInfoPresenter {

    private Context context = null;
    private NameInfoModelImp nameInfoModelImp = null;

    public NameInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.nameInfoModelImp = new NameInfoModelImp(context);
    }

    @Override
    public void getNameList(QueryNameInfo queryNameInfo) {
        nameInfoModelImp.getNameList(queryNameInfo, this);
    }

    @Override
    public void getCollectionList(QueryNameInfo queryNameInfo) {
        nameInfoModelImp.getCollectionList(queryNameInfo, this);
    }
}
