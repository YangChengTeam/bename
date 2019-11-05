package com.ydys.qmb.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.ydys.qmb.api.ChongMingInfoService;
import com.ydys.qmb.api.VersionInfoService;
import com.ydys.qmb.base.BaseModel;
import com.ydys.qmb.base.IBaseRequestCallBack;
import com.ydys.qmb.bean.AgentInfoRet;
import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.bean.ResultInfo;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class AgentInfoModelImp extends BaseModel implements AgentInfoModel<AgentInfoRet> {

    private Context context;
    private VersionInfoService versionInfoService;
    private CompositeSubscription mCompositeSubscription;

    public AgentInfoModelImp(Context context) {
        this.context = context;
        versionInfoService = mRetrofit.create(VersionInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void addAgent(String imei, String agentId, String siteId, IBaseRequestCallBack<AgentInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("imei", imei);
            params.put("agent", agentId);
            params.put("site_id", StringUtils.isEmpty(siteId) ? agentId : siteId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(versionInfoService.addAgent(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentInfoRet>() {
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
                    public void onNext(AgentInfoRet agentInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(agentInfoRet);
                    }
                }));
    }

}
