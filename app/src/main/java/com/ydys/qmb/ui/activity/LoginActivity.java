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
import com.blankj.utilcode.util.ToastUtils;
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

public class LoginActivity extends BaseActivity implements UserInfoView {

    public static String COUNTRY_CODE = "86";

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.et_phone_number)
    EditText mPhoneNumberEt;

    @BindView(R.id.et_validate_code)
    EditText mValidateCodeEt;

    @BindView(R.id.btn_get_validate)
    Button mGetValidateBtn;

    private EventHandler eventHandler;

    private ProgressDialog progressDialog = null;

    UserInfoPresenterImp userInfoPresenterImp;

    private UMShareAPI mShareAPI = null;

    private int loginType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                Logger.i("send code success--->");
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                Logger.i("send code fail--->");
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {

                                loginAndRegister();

                                Logger.i("validate code success--->");
                                // TODO 处理验证码验证通过的结果
//                                if (progressDialog != null && progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
                            } else {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toasty.normal(LoginActivity.this, "短信验证码错误，请重试").show();
                                Logger.i("validate code fail--->");
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };

        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mShareAPI = UMShareAPI.get(this);
        userInfoPresenterImp = new UserInfoPresenterImp(this, this);
    }

    @OnClick(R.id.btn_get_validate)
    public void getValidateCode() {

        if (!mGetValidateBtn.isClickable()) {
            return;
        }

        if (StringUtils.isEmpty(mPhoneNumberEt.getText())) {
            Toasty.normal(LoginActivity.this, "请输入手机号").show();
            return;
        }

        if (!RegexUtils.isMobileSimple(mPhoneNumberEt.getText())) {
            Toasty.normal(LoginActivity.this, "手机号格式错误").show();
            return;
        }

        startCountDown();
        SMSSDK.getVerificationCode(COUNTRY_CODE, mPhoneNumberEt.getText().toString());
    }

    public void startCountDown() {
        mGetValidateBtn.setClickable(false);
        mGetValidateBtn.setBackgroundResource(R.drawable.validate_click_bg);
        mGetValidateBtn.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_aaa));

        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                Logger.i("倒计时" + millisUntilFinished / 1000 + "秒");
                mGetValidateBtn.setText(millisUntilFinished / 1000 + " s");
            }

            public void onFinish() {
                Logger.i("倒计时完成");
                mGetValidateBtn.setText("获取验证码");
                mGetValidateBtn.setClickable(true);
                mGetValidateBtn.setBackgroundResource(R.drawable.validate_bg);
                mGetValidateBtn.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start();
    }

    @OnClick(R.id.btn_login)
    void login() {
        if (StringUtils.isEmpty(mPhoneNumberEt.getText())) {
            Toasty.normal(LoginActivity.this, "请输入手机号").show();
            return;
        }

        if (StringUtils.isEmpty(mValidateCodeEt.getText())) {
            Toasty.normal(LoginActivity.this, "请输入验证码").show();
            return;
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        SMSSDK.submitVerificationCode(COUNTRY_CODE, mPhoneNumberEt.getText().toString(), mValidateCodeEt.getText().toString());
    }

    public void loginAndRegister() {
        loginType = 1;
        LoginParams loginParams = new LoginParams();
        loginParams.setType(loginType);
        loginParams.setAgent(SPUtils.getInstance().getString(Constants.AGENT_ID,App.agentId));
        loginParams.setImei(PhoneUtils.getIMEI());
        loginParams.setPhone(mPhoneNumberEt.getText().toString());
        loginParams.setVersion(AppUtils.getAppVersionName());
        userInfoPresenterImp.login(loginParams);
    }

    @OnClick(R.id.iv_wx_login)
    public void wxLogin() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        loginType = 2;
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
    }

    @OnClick(R.id.iv_qq_login)
    public void qqLogin() {
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
            App.isLogin = true;
            if (data != null) {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }

                LoginParams loginParams = new LoginParams();
                loginParams.setType(loginType);
                loginParams.setAgent(App.agentId);
                loginParams.setImei(PhoneUtils.getIMEI());
                loginParams.setVersion(AppUtils.getAppVersionName());
                loginParams.setFace(data.get("iconurl"));
                loginParams.setNickname(data.get("name"));
                loginParams.setOpenid(data.get("uid"));
                userInfoPresenterImp.login(loginParams);
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
            Toasty.normal(LoginActivity.this, "授权失败").show();
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
            Toasty.normal(LoginActivity.this, "授权取消").show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
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
                Toasty.normal(LoginActivity.this, "登录成功").show();
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
    void term(){
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 1);
        startActivity(intent);
    }


    @OnClick(R.id.tv_privacy)
    void privacy(){
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 2);
        startActivity(intent);
    }
}
