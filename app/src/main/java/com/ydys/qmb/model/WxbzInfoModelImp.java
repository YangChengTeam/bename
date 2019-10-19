package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ydys.qmb.api.ScwgInfoService;
import com.ydys.qmb.api.WxbzInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.ScwgInfoRet;
import com.ydys.qmb.bean.WxbzInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class WxbzInfoModelImp extends BaseModel implements WxbzInfoModel<WxbzInfoRet> {

    private Context context;
    private WxbzInfoService wxbzInfoService;
    private CompositeSubscription mCompositeSubscription;

    public WxbzInfoModelImp(Context context) {
        this.context = context;
        wxbzInfoService = mRetrofit.create(WxbzInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getWxbzInfo(String xing, String ming, int year, int month, int days, int hours, IBaseRequestCallBack<WxbzInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("xing", xing);
            params.put("ming", ming);
            params.put("nf", year);
            params.put("yf", month);
            params.put("rq", days);
            params.put("hour", hours);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(wxbzInfoService.getWxbzInfo(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WxbzInfoRet>() {
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
                    public void onNext(WxbzInfoRet wxbzInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(wxbzInfoRet);
                    }
                }));
    }

}
