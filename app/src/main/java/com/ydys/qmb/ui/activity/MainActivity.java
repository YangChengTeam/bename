package com.ydys.qmb.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AgentInfoRet;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.VersionInfo;
import com.ydys.qmb.bean.VersionInfoRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.AgentInfoPresenterImp;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.presenter.VersionInfoPresenterImp;
import com.ydys.qmb.ui.adapter.MyFragmentAdapter;
import com.ydys.qmb.ui.custom.VersionUpdateDialog;
import com.ydys.qmb.ui.fragment.ExplainFragment;
import com.ydys.qmb.ui.fragment.FindFragment;
import com.ydys.qmb.ui.fragment.HomeFragment;
import com.ydys.qmb.ui.fragment.MyFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity implements IBaseView, VersionUpdateDialog.UpdateListener {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    VersionUpdateDialog updateDialog;

    private final int[] TITLES = new int[]{R.string.tab_home_txt, R.string.tab_category_txt, R.string.tab_find_txt, R.string.tab_my_txt};

    private final int[] IMAGES = new int[]{R.drawable.tab_qm_selector, R.drawable.tab_jm_selector, R.drawable.tab_test_selector, R.drawable.tab_my_selector};

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private MyFragmentAdapter adapter;

    private VersionInfoPresenterImp versionInfoPresenterImp;

    private AgentInfoPresenterImp agentInfoPresenterImp;

    private VersionInfo versionInfo;

    BaseDownloadTask task;

    private long clickTime = 0;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void showReadPhone() {
        readPhoneTask();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneDenied() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForReadPhone(PermissionRequest request) {
        showRationaleDialog(R.string.permission_read_phone_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showStorage() {
        Logger.i("允许使用存储权限--->");
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStorageDenied() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForStorage(PermissionRequest request) {
        showRationaleDialog(R.string.permission_storage_rationale, request);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStorageNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    public void readPhoneTask() {
        Logger.i("readPhoneTask--->");
        //将手机串号_渠道ID作为标识用户是否上报过数据
        String agentKey = PhoneUtils.getIMEI() + "_" + App.agentId;
        if (!SPUtils.getInstance().getBoolean(agentKey, false)) {
            agentInfoPresenterImp.addAgent(PhoneUtils.getIMEI(), App.agentId, App.siteId);
        }
        MainActivityPermissionsDispatcher.showStorageWithPermissionCheck(this);
    }

    @Override
    protected void initVars() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new ExplainFragment());
        mFragmentList.add(new FindFragment());
        mFragmentList.add(new MyFragment());
    }

    public void initDialog() {
        updateDialog = new VersionUpdateDialog(this, R.style.login_dialog);
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

    }

    @Override
    protected void initViews() {
        FileDownloader.setup(this);
        initDialog();
        setTabs(tabLayout, this.getLayoutInflater(), TITLES, IMAGES);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //绑定tab点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Logger.i("tab pos --->" + pos);
                viewPager.setCurrentItem(pos);
                if (pos == 0) {

                }
                if (pos == 1) {

                }
                if (pos == 3) {
//                    if (mFragmentList.get(pos) instanceof MyFragment) {
//                        ((MyFragment) mFragmentList.get(pos)).setUserInfo();
//                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //检测新版本
        versionInfoPresenterImp = new VersionInfoPresenterImp(this, this);
        agentInfoPresenterImp = new AgentInfoPresenterImp(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("main activity onresume--->");
        KeyboardUtils.hideSoftInput(this);
    }

    //设置回到主页
    public void setSelectFragment() {
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        MainActivityPermissionsDispatcher.showReadPhoneWithPermissionCheck(this);
        versionInfoPresenterImp.getNewVersion(App.agentId);
    }

    public void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] titles, int[] images) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);
            TextView tabText = view.findViewById(R.id.tv_tab);
            tabText.setText(titles[i]);
            ImageView tabImage = view.findViewById(R.id.iv_tab);
            tabImage.setImageResource(images[i]);
            tabLayout.addTab(tab);
        }
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object tData) {
        Logger.i(JSON.toJSONString(tData));

        if (tData != null) {
            if (tData instanceof VersionInfoRet) {
                if (((VersionInfoRet) tData).getData() != null && ((VersionInfoRet) tData).getCode() == Constants.SUCCESS) {
                    int currentCode = AppUtils.getAppVersionCode();
                    versionInfo = ((VersionInfoRet) tData).getData();
                    App.versionInfo = versionInfo;

                    if (versionInfo != null && versionInfo.getVersionCode() > currentCode) {
                        if (updateDialog != null && !updateDialog.isShowing()) {
                            updateDialog.setVersionCode(versionInfo.getVersionName());
                            updateDialog.setVersionContent(versionInfo.getUpdateContent());
                            updateDialog.setIsForceUpdate(versionInfo.getIsForce());
                            updateDialog.show();
                        }
                    } else {
                        //Toasty.normal(this, "已经是最新版本").show();
                        Logger.i("已经是最新版本--->" + currentCode);
                    }
                }
            }

            if (tData instanceof AgentInfoRet) {
                Logger.i("数据已上报完成--->" + JSON.toJSONString(tData));
                if (tData != null && ((AgentInfoRet) tData).getCode() == Constants.SUCCESS) {
                    SPUtils.getInstance().put(PhoneUtils.getIMEI() + "_" + App.agentId, true);
                    SPUtils.getInstance().put(Constants.AGENT_ID, ((AgentInfoRet) tData).getData().getAgent());
                }
                Logger.i("read agentid --->" + SPUtils.getInstance().getInt(Constants.AGENT_ID));
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    public void update() {
        if (versionInfo != null && !StringUtils.isEmpty(versionInfo.getUpdateUrl())) {
            downAppFile(versionInfo.getUpdateUrl());
        }
    }

    @Override
    public void updateCancel() {

    }

    public void downAppFile(String downUrl) {
        Logger.i("down url --->" + downUrl);

        final String filePath = PathUtils.getExternalAppFilesPath() + "/new_cool_app.apk";
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
                        Toasty.normal(MainActivity.this, "下载完成").show();
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
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            clickTime = System.currentTimeMillis();
            Toasty.normal(getApplicationContext(), "再按一次退出").show();
        } else {
            System.exit(0);
        }
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
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
}
