package com.ydys.qmb.api;

import com.ydys.qmb.bean.PopularInfoRet;
import com.ydys.qmb.bean.StructureInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface StructureInfoService {

    @POST("v1.explain/getStructure")
    Observable<StructureInfoRet> getStructureInfo(@Body RequestBody requestBody);

}
