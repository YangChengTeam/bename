package com.ydys.qmb.api;

import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.bean.NameInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface ChongMingInfoService {

    @POST("v1.explain/getchongm")
    Observable<ChongMingInfoRet> getChongMingInfo(@Body RequestBody requestBody);
}
