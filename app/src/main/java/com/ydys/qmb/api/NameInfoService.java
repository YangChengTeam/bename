package com.ydys.qmb.api;

import com.ydys.qmb.bean.AddKeepInfoRet;
import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.bean.NameInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface NameInfoService {

    @POST("v1.explain/surname")
    Observable<NameInfoRet> getNameList(@Body RequestBody requestBody);

    @POST("v1.explain/namedetail")
    Observable<NameDetailRet> getNameDetail(@Body RequestBody requestBody);

    @POST("v1.user/mycollect")
    Observable<NameInfoRet> getCollectionList(@Body RequestBody requestBody);

    @POST("v1.explain/upCollect")
    Observable<AddKeepInfoRet> addKeep(@Body RequestBody requestBody);

}
