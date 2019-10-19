package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.bean.LoginParams;
import com.ydys.qmb.bean.UserInfoRet;
import com.ydys.qmb.bean.VersionInfoRet;
import com.ydys.qmb.model.UserInfoModelImp;
import com.ydys.qmb.model.VersionInfoModelImp;
import com.ydys.qmb.view.UserInfoView;
import com.ydys.qmb.view.VersionInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class UserInfoPresenterImp extends BasePresenterImp<UserInfoView, UserInfoRet> implements UserInfoPresenter {

    private Context context = null;
    private UserInfoModelImp userInfoModelImp = null;

    public UserInfoPresenterImp(UserInfoView view, Context context) {
        super(view);
        this.context = context;
        this.userInfoModelImp = new UserInfoModelImp(context);
    }

    @Override
    public void login(LoginParams loginParams) {
        userInfoModelImp.login(loginParams, this);
    }
}
