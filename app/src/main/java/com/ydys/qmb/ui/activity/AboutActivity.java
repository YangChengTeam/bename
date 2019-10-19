package com.ydys.qmb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.tv_kefu_number)
    TextView mKeFuNumberTv;

    @BindView(R.id.tv_email)
    TextView mEmailTv;

    @BindView(R.id.tv_version_code)
    TextView mVersionCodeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleTextView.setText("关于我们");
        mVersionCodeTv.setText("V" + AppUtils.getAppVersionName());
        if (App.versionInfo != null) {
            mKeFuNumberTv.setText(App.versionInfo.getKfway() + "");
            mEmailTv.setText(App.versionInfo.getKfemail());
        }
    }

    @OnClick(R.id.layout_terms)
    void terms() {
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 1);
        startActivity(intent);
    }

    @OnClick(R.id.layout_privacy)
    void privacy() {
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("show_type", 2);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
