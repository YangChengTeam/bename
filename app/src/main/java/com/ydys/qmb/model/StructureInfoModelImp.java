package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.StructureInfoService;
import com.ydys.qmb.api.VersionInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.StructureInfoRet;
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

public class StructureInfoModelImp extends BaseModel implements StructureInfoModel<StructureInfoRet> {

    private Context context;
    private StructureInfoService structureInfoService;
    private CompositeSubscription mCompositeSubscription;

    public StructureInfoModelImp(Context context) {
        this.context = context;
        structureInfoService = mRetrofit.create(StructureInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getStructureInfo(String nameId, String xing, String ming, IBaseRequestCallBack<StructureInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("name_id", nameId);
            params.put("xing", xing);
            params.put("ming", ming);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(structureInfoService.getStructureInfo(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<StructureInfoRet>() {
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
                    public void onNext(StructureInfoRet structureInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(structureInfoRet);
                    }
                }));
    }

}