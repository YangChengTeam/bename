package com.ydys.qmb.api;

import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.bean.PopularInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface PopularInfoService {

    @POST("v1.explain/getPopular")
    Observable<PopularInfoRet> getPopularInfo(@Body RequestBody requestBody);

}
