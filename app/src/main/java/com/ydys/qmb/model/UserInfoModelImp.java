package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.UserInfoService;
import com.ydys.qmb.api.VersionInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.LoginParams;
import com.ydys.qmb.bean.UserInfoRet;
import com.ydys.qmb.bean.VersionInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class UserInfoModelImp extends BaseModel implements UserInfoModel<UserInfoRet> {

    private Context context;
    private UserInfoService userInfoService;
    private CompositeSubscription mCompositeSubscription;

    public UserInfoModelImp(Context context) {
        this.context = context;
        userInfoService = mRetrofit.create(UserInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void login(LoginParams loginParams, IBaseRequestCallBack<UserInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("type", loginParams.getType() + "");
            params.put("agent",loginParams.getAgent());
            params.put("version",loginParams.getVersion());
            params.put("imei",loginParams.getImei());
            params.put("phone",loginParams.getPhone()+"");
            params.put("face",loginParams.getFace());
            params.put("nick_name",loginParams.getNickname());
            params.put("openid",loginParams.getOpenid());
            params.put("site_id",loginParams.getSiteId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(userInfoService.login(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserInfoRet>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UserInfoRet userInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(userInfoRet);
                    }
                }));
    }
}
