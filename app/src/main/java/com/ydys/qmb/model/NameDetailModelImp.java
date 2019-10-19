package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.NameInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class NameDetailModelImp extends BaseModel implements NameDetailModel<NameDetailRet> {

    private Context context;
    private NameInfoService nameInfoService;
    private CompositeSubscription mCompositeSubscription;

    public NameDetailModelImp(Context context) {
        this.context = context;
        nameInfoService = mRetrofit.create(NameInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getNameDetail(String xing, String ming, String userId,IBaseRequestCallBack<NameDetailRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("xing", xing);
            params.put("ming", ming);
            params.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(nameInfoService.getNameDetail(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NameDetailRet>() {
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
                    public void onNext(NameDetailRet nameInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(nameInfoRet);
                    }
                }));
    }


}
