package com.ydys.qmb.api;

import com.ydys.qmb.bean.AgentInfoRet;
import com.ydys.qmb.bean.ResultInfo;
import com.ydys.qmb.bean.VersionInfoRet;

import javax.xml.transform.Result;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface VersionInfoService {

    @POST("v1.appinfo/versionInfo")
    Observable<VersionInfoRet> getNewVersion(@Body RequestBody requestBody);

    @POST("v1.appinfo/index")
    Observable<AgentInfoRet> addAgent(@Body RequestBody requestBody);
}
