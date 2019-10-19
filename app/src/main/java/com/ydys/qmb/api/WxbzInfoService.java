package com.ydys.qmb.api;

import com.ydys.qmb.bean.ScwgInfoRet;
import com.ydys.qmb.bean.WxbzInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface WxbzInfoService {

    @POST("v1.explain/getBzWx")
    Observable<WxbzInfoRet> getWxbzInfo(@Body RequestBody requestBody);

}
