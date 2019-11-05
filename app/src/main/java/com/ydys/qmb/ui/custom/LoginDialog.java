package com.ydys.qmb.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.LoginParams;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.UserInfoRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.UserInfoPresenterImp;
import com.ydys.qmb.ui.activity.Login1Activity;
import com.ydys.qmb.ui.activity.ProblemActivity;
import com.ydys.qmb.view.UserInfoView;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import es.dmoral.toasty.Toasty;


public class LoginDialog extends Dialog implements View.OnClickListener, UserInfoView {

    private Context mContext;

    private ImageView mCloseIv;

    LinearLayout mWxLayout;

    LinearLayout mQQLayout;

    LinearLayout mPhoneLayout;

    TextView mPrivacyTv;

    private ProgressDialog progressDialog = null;

    UserInfoPresenterImp userInfoPresenterImp;

    private UMShareAPI mShareAPI = null;

    private int loginType = 1;

    public LoginDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LoginDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在登录");
        mShareAPI = UMShareAPI.get(mContext);
        userInfoPresenterImp = new UserInfoPresenterImp(this, mContext);

        mWxLayout = findViewById(R.id.layout_wx);
        mQQLayout = findViewById(R.id.layout_qq);
        mPhoneLayout = findViewById(R.id.layout_phone);
        mPrivacyTv = findViewById(R.id.tv_privacy);

        mCloseIv = findViewById(R.id.iv_close);
        setCanceledOnTouchOutside(false);
        mCloseIv.setOnClickListener(this);
        mWxLayout.setOnClickListener(this);
        mQQLayout.setOnClickListener(this);
        mPhoneLayout.setOnClickListener(this);
        mPrivacyTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_wx:
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }

                loginType = 2;
                mShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.layout_qq:
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }

                loginType = 3;
                mShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.layout_phone:
                Intent intent = new Intent(mContext, Login1Activity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_privacy:
                Intent intent1 = new Intent(mContext, ProblemActivity.class);
                intent1.putExtra("show_type", 1);
                mContext.startActivity(intent1);
                break;
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.i("auth data --->" + JSONObject.toJSONString(data));
            //Toast.makeText(mContext, "授权成功了", Toast.LENGTH_LONG).show();
            try {
                App.isLogin = true;
                if (data != null) {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                    try {
                        LoginParams loginParams = new LoginParams();
                        loginParams.setType(loginType);
                        loginParams.setAgent(SPUtils.getInstance().getInt(Constants.AGENT_ID, Integer.parseInt(App.agentId)) + "");
                        loginParams.setImei(PhoneUtils.getIMEI());
                        loginParams.setVersion(AppUtils.getAppVersionName());
                        loginParams.setFace(data.get("iconurl"));
                        loginParams.setNickname(data.get("name"));
                        loginParams.setOpenid(data.get("uid"));
                        loginParams.setSiteId(App.siteId);

                        userInfoPresenterImp.login(loginParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //Toast.makeText(mContext, "授权失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            Toasty.normal(mContext, "授权失败").show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toasty.normal(mContext, "授权取消").show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Logger.i(JSON.toJSONString(tData));

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                Toasty.normal(mContext, "登录成功").show();
                //存储用户信息
                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));
                App.mUserInfo = tData.getData();
                App.isLogin = true;

                MessageEvent messageEvent = new MessageEvent("login_success");
                EventBus.getDefault().post(messageEvent);
            }
        }
        dismiss();
    }

    @Override
    public void loadDataError(Throwable throwable) {
        dismiss();
    }
}