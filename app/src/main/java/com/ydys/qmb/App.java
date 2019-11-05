package com.ydys.qmb;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.ydys.qmb.bean.PriceInfo;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.bean.VersionInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.util.AppContextUtil;

/**
 * Created by admin on 2017/4/7.
 */

public class App extends Application {

    protected static App mInstance;

    public static Context applicationContext;

    public static UserInfo mUserInfo;

    public static boolean isLogin;

    public static String agentId = "1";

    public static String siteId = "";

    public static PriceInfo priceInfo;//商品价格信息

    public static VersionInfo versionInfo;

    public static Context getContext() {
        return applicationContext;
    }

    public App() {
        mInstance = this;
    }

    public static App getApp() {
        if (mInstance != null && mInstance instanceof App) {
            return (App) mInstance;
        } else {
            mInstance = new App();
            mInstance.onCreate();
            return (App) mInstance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        //获取渠道信息
        String channel = AppContextUtil.getChannel(this);
        try {
            if (!StringUtils.isEmpty(channel)) {
                JSONObject jsonObject = JSON.parseObject(channel);
                App.agentId = jsonObject.getString("agent_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.i("res channel--->" + App.agentId);

        //获取渠道信息
        String siteInfo = AppContextUtil.getSiteInfo(this);
        try {
            if (!StringUtils.isEmpty(siteInfo)) {
                JSONObject jsonObject = JSON.parseObject(siteInfo);
                App.siteId = jsonObject.getString("site_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        UMConfigure.init(this,"5d858ba63fc195f8e6000416",App.agentId,UMConfigure.DEVICE_TYPE_PHONE, "");

        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        PlatformConfig.setQQZone("1109891315", "C1DHLZgL4oVaTZSd");
        PlatformConfig.setWeixin("wx096649d1b91ee026", "ee1b30b2796f3446a0c33aed76206fa0");

        loadUserInfo();
    }

    public void loadUserInfo() {
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            mUserInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
            App.isLogin = true;
        }
    }
}
