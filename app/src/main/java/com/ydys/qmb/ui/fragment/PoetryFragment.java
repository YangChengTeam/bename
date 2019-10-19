package com.ydys.qmb.ui.fragment;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.PoetryInfoRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.PoetryInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.view.PoetryInfoView;

import butterknife.BindView;

public class PoetryFragment extends BaseFragment implements PoetryInfoView {

    @BindView(R.id.layout_poetry_info)
    LinearLayout mPoetryLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.tv_poetry_title)
    TextView mPoetryTitleTv;

    @BindView(R.id.tv_author)
    TextView mAuthorTv;

    @BindView(R.id.tv_chaodai)
    TextView mChaodaiTv;

    @BindView(R.id.tv_sc_type)
    TextView mScTypeTv;

    @BindView(R.id.tv_poetry)
    TextView mPoetryTv;

    @BindView(R.id.tv_old_poetry)
    TextView mOldPoetryTv;

    PoetryInfoPresenterImp poetryInfoPresenterImp;

    private NameDetailActivity activity;

    @Override
    protected int getContentView() {
        return R.layout.fragment_peotry;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        activity = (NameDetailActivity) getActivity();
    }

    @Override
    public void loadData() {
        poetryInfoPresenterImp = new PoetryInfoPresenterImp(this, getActivity());
        poetryInfoPresenterImp.getPoetryInfo(activity.getQueryNameId());
    }

    @Override
    protected void initFragmentConfig() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(PoetryInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() == null || StringUtils.isEmpty(tData.getData().getTitle())) {
                mPoetryLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            } else {
                mPoetryLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);

                mPoetryTitleTv.setText(tData.getData().getTitle());
                mAuthorTv.setText(tData.getData().getAuthor());
                mChaodaiTv.setText(tData.getData().getDynasty());
                mScTypeTv.setText(tData.getData().getType());
                mPoetryTv.setText(tData.getData().getStanza());
                mOldPoetryTv.setText(Html.fromHtml(tData.getData().getContent()));
            }
        } else {
            mPoetryLayout.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        mPoetryLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }
}
