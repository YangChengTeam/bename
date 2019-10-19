package com.ydys.qmb.api;

import com.ydys.qmb.bean.HotWordRet;
import com.ydys.qmb.bean.UserInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface UserInfoService {

    @POST("v1.user/login")
    Observable<UserInfoRet> login(@Body RequestBody requestBody);
}
