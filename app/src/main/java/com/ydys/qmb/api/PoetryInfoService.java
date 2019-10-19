package com.ydys.qmb.api;

import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.PoetryInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface PoetryInfoService {

    @POST("v1.explain/getshici")
    Observable<PoetryInfoRet> getPoetryInfo(@Body RequestBody requestBody);

}
