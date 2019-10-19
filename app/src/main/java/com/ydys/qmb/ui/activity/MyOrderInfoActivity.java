package com.ydys.qmb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.HeadInfoRet;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.OrderInfo;
import com.ydys.qmb.bean.OrderInfoRet;
import com.ydys.qmb.bean.PayRequestParams;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.MyOrderInfoPresenterImp;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.ui.adapter.MyOrderInfoAdapter;
import com.ydys.qmb.ui.custom.NormalDecoration;
import com.ydys.qmb.view.OrderInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyOrderInfoActivity extends BaseActivity implements OrderInfoView {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.order_list)
    RecyclerView mOrderListView;

    @BindView(R.id.spin_kit)
    SpinKitView loadView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    MyOrderInfoAdapter myOrderInfoAdapter;

    MyOrderInfoPresenterImp myOrderInfoPresenterImp;

    private int currentPage = 1;

    private int pageSize = 20;

    private UserInfo mUserInfo;

    private PayRequestParams orderParams;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
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
        mTitleTv.setText("我的订单");

        myOrderInfoAdapter = new MyOrderInfoAdapter(this, null);
        mOrderListView.setLayoutManager(new LinearLayoutManager(this));
        mOrderListView.addItemDecoration(new NormalDecoration(0, SizeUtils.dp2px(6)));
        mOrderListView.setAdapter(myOrderInfoAdapter);

        myOrderInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;

                orderParams.setPage(currentPage);
                myOrderInfoPresenterImp.myOrderList(orderParams);
            }
        }, mOrderListView);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myOrderInfoPresenterImp = new MyOrderInfoPresenterImp(this, this);
        if (App.mUserInfo != null) {
            mUserInfo = App.mUserInfo;

            orderParams = new PayRequestParams();
            orderParams.setUser_id(mUserInfo.getId());
            orderParams.setType(mUserInfo.getLogin_type());
            orderParams.setPhone(mUserInfo.getPhone());
            orderParams.setOpenid(mUserInfo.getOpenid());
            orderParams.setPage(currentPage);

            myOrderInfoPresenterImp.myOrderList(orderParams);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
        loadView.setVisibility(View.GONE);
    }

    @Override
    public void loadDataSuccess(OrderInfoRet tData) {
        loadView.setVisibility(View.GONE);
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (currentPage == 1) {
                if (tData.getData().size() > 0) {
                    mNoDataLayout.setVisibility(View.GONE);
                    mOrderListView.setVisibility(View.VISIBLE);
                    myOrderInfoAdapter.setNewData(tData.getData());
                } else {
                    mNoDataLayout.setVisibility(View.VISIBLE);
                    mOrderListView.setVisibility(View.GONE);
                }
            } else {
                myOrderInfoAdapter.addData(tData.getData());
            }

            if (tData.getData().size() == pageSize) {
                myOrderInfoAdapter.loadMoreComplete();
            } else {
                myOrderInfoAdapter.loadMoreEnd(true);
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        loadView.setVisibility(View.GONE);
        if (currentPage == 1) {
            mNoDataLayout.setVisibility(View.VISIBLE);
            mOrderListView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
