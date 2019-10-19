package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.ChongMingInfoService;
import com.ydys.qmb.api.NameInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.bean.NameDetailRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class ChongMingInfoModelImp extends BaseModel implements ChongMingInfoModel<ChongMingInfoRet> {

    private Context context;
    private ChongMingInfoService chongMingInfoService;
    private CompositeSubscription mCompositeSubscription;

    public ChongMingInfoModelImp(Context context) {
        this.context = context;
        chongMingInfoService = mRetrofit.create(ChongMingInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getChongMingInfo(String nameId, IBaseRequestCallBack<ChongMingInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("name_id", nameId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(chongMingInfoService.getChongMingInfo(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ChongMingInfoRet>() {
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
                    public void onNext(ChongMingInfoRet chongMingInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(chongMingInfoRet);
                    }
                }));
    }

}
