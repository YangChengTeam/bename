package com.ydys.qmb.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.bean.VersionInfo;
import com.ydys.qmb.bean.VersionInfoRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.VersionInfoPresenterImp;
import com.ydys.qmb.ui.activity.AboutActivity;
import com.ydys.qmb.ui.activity.Login1Activity;
import com.ydys.qmb.ui.activity.MyCollectionActivity;
import com.ydys.qmb.ui.activity.MyOrderInfoActivity;
import com.ydys.qmb.ui.activity.ProblemActivity;
import com.ydys.qmb.ui.custom.ConfigDialog;
import com.ydys.qmb.ui.custom.GlideCircleTransformWithBorder;
import com.ydys.qmb.ui.custom.VersionUpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by admin on 2017/4/20.
 */

@RuntimePermissions
public class MyFragment extends BaseFragment implements VersionUpdateDialog.UpdateListener, View.OnClickListener, IBaseView, ConfigDialog.ConfigListener {

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadIv;

    @BindView(R.id.tv_user_nick_name)
    TextView mUserNickNameTv;

    @BindView(R.id.tv_user_id)
    TextView mUserIdTv;

    @BindView(R.id.layout_login_out)
    LinearLayout mLoginOutLayout;

    @BindView(R.id.iv_user_right)
    ImageView mUserRightIv;

    @BindView(R.id.layout_order)
    LinearLayout mMyOrderLayout;

    @BindView(R.id.layout_my_collection)
    LinearLayout mMyCollectionLayout;

    BaseDownloadTask task;

    VersionUpdateDialog updateDialog;

    private VersionInfo versionInfo;

    private UserInfo mUserInfo;

    BottomSheetDialog bottomSheetDialog;

    private VersionInfoPresenterImp versionInfoPresenterImp;

    private ShareAction shareAction;

    private ProgressDialog progressDialog = null;

    private UMShareAPI mShareAPI = null;

    private Tencent mTencent;

    private String downUrl;

    ConfigDialog deleteConfig;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    int progress = (Integer) msg.obj;
                    updateDialog.setProgress(progress);
                    break;
                case 1:
                    updateDialog.downFinish();
                    break;
                default:
                    break;
            }
        }
    };

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void showReadPhone() {
        MyFragmentPermissionsDispatcher.showStorageWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneDenied() {
        Toast.makeText(getActivity(), R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForReadPhone(PermissionRequest request) {
        showRationaleDialog(R.string.permission_read_phone_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneNeverAskAgain() {
        Toast.makeText(getActivity(), R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }


    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showStorage() {
        Logger.i("允许使用存储权限--->");
        if (shareAction != null) {
            shareAction.setPlatform(SHARE_MEDIA.QQ).share();
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStorageDenied() {
        Toast.makeText(getActivity(), R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForStorage(PermissionRequest request) {
        showRationaleDialog(R.string.permission_storage_rationale, request);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStorageNeverAskAgain() {
        Toast.makeText(getActivity(), R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initVars() {
    }

    @Override
    public void initViews() {
        mShareAPI = UMShareAPI.get(getActivity());
        FileDownloader.setup(getActivity());
        updateDialog = new VersionUpdateDialog(getActivity(), R.style.login_dialog);
        updateDialog.setUpdateListener(this);
        updateDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (versionInfo != null && versionInfo.getIsForce() == 1) {
                        return true;//不执行父类点击事件
                    }
                    return false;
                }
                return false;
            }
        });

        versionInfoPresenterImp = new VersionInfoPresenterImp(this, getActivity());

        initDialog();
    }

    public void initDialog() {

        deleteConfig = new ConfigDialog(getActivity(), R.style.login_dialog);
        deleteConfig.setConfigListener(this);

        if (shareAction == null) {
            if (shareAction == null) {
                shareAction = new ShareAction(getActivity());
                shareAction.setCallback(shareListener);//回调监听器

                UMWeb web = new UMWeb("http://qm.bshu.com/static/index.html");
                web.setTitle("好名陪伴一生，为您的宝宝起一个好听的名字！");//标题
                web.setThumb(new UMImage(getActivity(), R.drawable.app_share));  //缩略图
                web.setDescription("科学智能宝宝起名平台，大数据为您推荐满分好名，起到您满意为止。");//描述
                shareAction.withMedia(web);
            }
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在分享");

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View shareView = LayoutInflater.from(getActivity()).inflate(R.layout.share_dialog_view, null);
        ImageView mCloseImageView = shareView.findViewById(R.id.iv_close_share);

        LinearLayout weixinLayout = shareView.findViewById(R.id.layout_weixin);
        LinearLayout circleLayout = shareView.findViewById(R.id.layout_circle);
        LinearLayout qqLayout = shareView.findViewById(R.id.layout_qq_friends);
        LinearLayout qzoneLayout = shareView.findViewById(R.id.layout_qzone);
        weixinLayout.setOnClickListener(this);
        circleLayout.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        qzoneLayout.setOnClickListener(this);
        mCloseImageView.setOnClickListener(this);

        bottomSheetDialog.setContentView(shareView);
    }

    @Override
    public void loadData() {
        EventBus.getDefault().register(this);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Logger.i("isVisibleToUser--->" + isVisibleToUser);
//        if (isVisibleToUser && getActivity() != null) {
//            setUserInfo();
//        }
//    }

    public void setUserInfo() {
        if (App.mUserInfo != null) {
            mUserInfo = App.mUserInfo;

            RequestOptions options = new RequestOptions().skipMemoryCache(true);
            options.placeholder(R.mipmap.user_def_head);
            options.transform(new GlideCircleTransformWithBorder(getActivity(), 2, ContextCompat.getColor(getActivity(), R.color.white)));
            Glide.with(getActivity()).load(mUserInfo.getFace()).apply(options).into(mUserHeadIv);


            mMyOrderLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(44)));
            mMyCollectionLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(44)));

            mUserNickNameTv.setText(mUserInfo.getNickname());
            mUserIdTv.setText("用户ID：" + mUserInfo.getId());
            mUserRightIv.setVisibility(View.GONE);
            mLoginOutLayout.setVisibility(View.VISIBLE);
            mMyOrderLayout.setVisibility(View.VISIBLE);
            mMyCollectionLayout.setVisibility(View.VISIBLE);
        }else{
            disableUserInfo();
        }
    }

    public void disableUserInfo() {
        RequestOptions options = new RequestOptions().skipMemoryCache(true);
        options.placeholder(R.mipmap.user_def_head);
        Glide.with(getActivity()).load(R.mipmap.user_def_head).apply(options).into(mUserHeadIv);
        mUserNickNameTv.setText("未登录");
        mUserIdTv.setText("登录后体验更多功能");
        mUserRightIv.setVisibility(View.VISIBLE);
        mLoginOutLayout.setVisibility(View.GONE);
        mMyOrderLayout.setVisibility(View.GONE);
        mMyCollectionLayout.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
        KeyboardUtils.hideSoftInput(getActivity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("login_success")) {
            setUserInfo();
        }
    }

    @OnClick(R.id.layout_user_info)
    void register() {
        if (App.mUserInfo == null) {
            Intent intent = new Intent(getActivity(), Login1Activity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layout_share)
    void share() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @OnClick(R.id.layout_market)
    void market() {
        Toasty.normal(getActivity(), "敬请期待").show();
        //GoToScoreUtils.goToMarket(getActivity(), AppUtils.getAppPackageName());
    }

    @OnClick(R.id.layout_login_out)
    void loginOut() {
        if (deleteConfig != null && !deleteConfig.isShowing()) {
            deleteConfig.show();
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            dismissShareView();
            Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Logger.i(t.getMessage());
            dismissShareView();
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            dismissShareView();
            Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void initFragmentConfig() {

    }

    @OnClick(R.id.layout_about_us)
    void aboutUs() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_order)
    void myOrder() {
        Intent intent = new Intent(getActivity(), MyOrderInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_my_collection)
    void myCollection() {
        Intent intent = new Intent(getActivity(), MyCollectionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_problem)
    void problem() {
        Intent intent = new Intent(getActivity(), ProblemActivity.class);
        intent.putExtra("show_type", 0);
        startActivity(intent);
    }

    @OnClick(R.id.layout_version_update)
    void versionCheck() {

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage("正在检测新版本");
            progressDialog.show();
        }

        versionInfoPresenterImp.getNewVersion(App.agentId);
    }

    public void downAppFile(String downUrl) {
        final String filePath = PathUtils.getExternalAppFilesPath() + "/new_qmb.apk";
        Logger.i("down app path --->" + filePath);

        task = FileDownloader.getImpl().create(downUrl)
                .setPath(filePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //Toasty.normal(SettingActivity.this, "正在更新版本后...").show();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int progress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                        Logger.i("progress--->" + soFarBytes + "---" + totalBytes + "---" + progress);

                        Message message = new Message();
                        message.what = 0;
                        message.obj = progress;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Toasty.normal(getActivity(), "下载完成").show();
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);

                        AppUtils.installApp(filePath);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                });

        task.start();
    }

    @Override
    public void update() {
        //开始下载
        downAppFile(downUrl);
    }

    @Override
    public void updateCancel() {

    }

    public void dismissShareView() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        boolean isShow = true;
        switch (v.getId()) {
            case R.id.layout_weixin:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();
                break;
            case R.id.layout_circle:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
                break;
            case R.id.layout_qq_friends:
                isShow = false;
                MyFragmentPermissionsDispatcher.showReadPhoneWithPermissionCheck(this);
                break;
            case R.id.layout_qzone:
                shareAction.setPlatform(SHARE_MEDIA.QZONE).share();
                break;
            case R.id.iv_close_share:
                isShow = false;
                dismissShareView();
                break;
            default:
                break;
        }

        if (isShow && progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object tData) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (tData != null) {
            if (tData instanceof VersionInfoRet) {
                if (((VersionInfoRet) tData).getData() != null && ((VersionInfoRet) tData).getCode() == Constants.SUCCESS) {
                    int currentCode = AppUtils.getAppVersionCode();
                    versionInfo = ((VersionInfoRet) tData).getData();
                    App.versionInfo = versionInfo;

                    if (versionInfo != null && versionInfo.getVersionCode() > currentCode) {
                        if (updateDialog != null && !updateDialog.isShowing()) {
                            downUrl = versionInfo.getUpdateUrl();
                            updateDialog.setVersionCode(versionInfo.getVersionName());
                            updateDialog.setVersionContent(versionInfo.getUpdateContent());
                            updateDialog.setIsForceUpdate(versionInfo.getIsForce());
                            updateDialog.show();
                        }
                    } else {
                        Toasty.normal(getActivity(), "已经是最新版本").show();
                        Logger.i("已经是最新版本--->" + currentCode);
                    }
                } else {
                    Toasty.normal(getActivity(), "版本检测失败，请稍后重试").show();
                }
            }
        }

    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toasty.normal(getActivity(), "版本检测失败，请稍后重试").show();
    }

    @Override
    public void config() {
        //确认退出
        SPUtils.getInstance().put(Constants.USER_INFO, "");
        App.mUserInfo = null;
        App.isLogin = false;

        disableUserInfo();

        mShareAPI.deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN, null);
        mShareAPI.deleteOauth(getActivity(), SHARE_MEDIA.QQ, null);
    }

    @Override
    public void cancel() {

    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

