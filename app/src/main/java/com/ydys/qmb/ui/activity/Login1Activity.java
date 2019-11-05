package com.ydys.qmb.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.LoginParams;
import com.ydys.qmb.bean.UserInfoRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.presenter.UserInfoPresenterImp;
import com.ydys.qmb.view.UserInfoView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import es.dmoral.toasty.Toasty;

/**
 * Created by admin on 2017/4/20.
 */

public class Login1Activity extends BaseActivity implements UserInfoView {

    public static String COUNTRY_CODE = "86";

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.iv_agree)
    ImageView mAgreeIv;

    private ProgressDialog progressDialog = null;

    UserInfoPresenterImp userInfoPresenterImp;

    private UMShareAPI mShareAPI = null;

    private int loginType = 1;

    private boolean isAgree = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login1;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTextView.setText("快捷登录");
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mShareAPI = UMShareAPI.get(this);
        userInfoPresenterImp = new UserInfoPresenterImp(this, this);
    }

    @OnClick(R.id.iv_agree)
    void agree() {
        isAgree = !isAgree;
        mAgreeIv.setImageResource(isAgree ? R.mipmap.is_agree : R.mipmap.is_not_agree);
    }

    @OnClick(R.id.layout_wx_login)
    public void wxLogin() {
        if (!isAgree) {
            Toasty.normal(Login1Activity.this, "请阅读并同意隐私协议后继续登录").show();
            return;
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        loginType = 2;
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
    }

    @OnClick(R.id.layout_qq_login)
    public void qqLogin() {
        if (!isAgree) {
            Toasty.normal(Login1Activity.this, "请阅读并同意隐私协议后继续登录").show();
            return;
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        loginType = 3;
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
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
                    SPUtils.getInstance().getInt(Constants.AGENT_ID);
                    Logger.i("login agent --->" + SPUtils.getInstance().getInt(Constants.AGENT_ID));
                    Logger.i("login agentid --->" + SPUtils.getInstance().getInt(Constants.AGENT_ID, Integer.parseInt(App.agentId)));

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
            Toasty.normal(Login1Activity.this, "授权失败").show();
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
            Toasty.normal(Login1Activity.this, "授权取消").show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

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
                Toasty.normal(Login1Activity.this, "登录成功").show();
                //存储用户信息
                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));
                App.mUserInfo = tData.getData();
                App.isLogin = true;
                finish();
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.tv_term)
    void term() {
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 1);
        startActivity(intent);
    }


    @OnClick(R.id.tv_privacy)
    void privacy() {
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 2);
        startActivity(intent);
    }
}
