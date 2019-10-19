package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.PoetryInfoService;
import com.ydys.qmb.api.VipInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.PayInfoRet;
import com.ydys.qmb.bean.PayRequestParams;
import com.ydys.qmb.bean.PoetryInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class PayInfoModelImp extends BaseModel implements PayInfoModel<PayInfoRet> {

    private Context context;
    private VipInfoService vipInfoService;
    private CompositeSubscription mCompositeSubscription;

    public PayInfoModelImp(Context context) {
        this.context = context;
        vipInfoService = mRetrofit.create(VipInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void createOrder(PayRequestParams payParams, IBaseRequestCallBack<PayInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", payParams.getUser_id());
            params.put("type", payParams.getType() + "");
            params.put("phone", payParams.getPhone() + "");
            params.put("openid", payParams.getOpenid());
            params.put("pay_way", payParams.getPay_way());
            params.put("amount", payParams.getAmount() + "");
            params.put("xing", payParams.getXing());
            params.put("title", payParams.getTitle());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        mCompositeSubscription.add(vipInfoService.createOrder(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PayInfoRet>() {
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
                    public void onNext(PayInfoRet payInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(payInfoRet);
                    }
                }));
    }

}