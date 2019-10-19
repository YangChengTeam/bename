package com.ydys.qmb.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.ydys.qmb.presenter.Presenter;
import com.umeng.analytics.MobclickAgent;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBar();
        context = this;
        ButterKnife.bind(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        initVars();
        initViews();
        initData(savedInstanceState);
    }

    public void setStatusBar() {
        //StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
        //invadeStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (presenter == null && getPresenter() != null) {
            presenter = getPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null) {
            presenter.destroy();
        }
    }

    public Context getContext() {
        return context;
    }

    protected abstract int getLayoutId();

    protected abstract Presenter getPresenter();

    protected abstract void initVars();

    protected abstract void initViews();

    protected abstract void initData(Bundle savedInstanceState);
}
