package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AddKeepInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.model.AddKeepInfoModelImp;

/**
 * Created by admin on 2017/4/7.
 */

public class AddKeepInfoPresenterImp extends BasePresenterImp<IBaseView, AddKeepInfoRet> implements AddKeepInfoPresenter {

    private Context context = null;
    private AddKeepInfoModelImp addKeepInfoModelImp = null;

    public AddKeepInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.addKeepInfoModelImp = new AddKeepInfoModelImp(context);
    }

    @Override
    public void addKeep(QueryNameInfo queryNameInfo) {
        addKeepInfoModelImp.addKeep(queryNameInfo, this);
    }
}
