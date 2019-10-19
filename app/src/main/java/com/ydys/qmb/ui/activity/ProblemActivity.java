package com.ydys.qmb.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.ydys.qmb.R;
import com.ydys.qmb.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/20.
 */

public class ProblemActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.layout_ad)
    LinearLayout mAdLayout;

    AgentWeb mAgentWeb;

    String[] titles = {"问题帮助", "隐私政策", "服务协议"};

    String[] urls = {"http://qm.bshu.com/static/help.html", "http://qm.bshu.com/static/privacy.html", "http://qm.bshu.com/static/terms.html"};

    private int showType = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_problem;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showType = bundle.getInt("show_type", 0);
        }

        mTitleTextView.setText(titles[showType]);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mAdLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(urls[showType]);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
