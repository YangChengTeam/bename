package com.ydys.qmb.api;

import com.ydys.qmb.bean.AgentInfoRet;
import com.ydys.qmb.bean.BuyVipRet;
import com.ydys.qmb.bean.OrderInfoRet;
import com.ydys.qmb.bean.PayInfoRet;
import com.ydys.qmb.bean.PriceInfo;
import com.ydys.qmb.bean.PriceInfoRet;
import com.ydys.qmb.bean.VersionInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface VipInfoService {

    @POST("v1.appinfo/priceinfo")
    Observable<PriceInfoRet> vipPrice(@Body RequestBody requestBody);

    @POST("v1.pay/payorder")
    Observable<PayInfoRet> createOrder(@Body RequestBody requestBody);

    @POST("v1.user/getUserPayInfo")
    Observable<BuyVipRet> isBuyVip(@Body RequestBody requestBody);

    @POST("v1.user/myorder")
    Observable<OrderInfoRet> myOrderList(@Body RequestBody requestBody);

}
