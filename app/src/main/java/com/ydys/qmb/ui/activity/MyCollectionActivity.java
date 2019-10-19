package com.ydys.qmb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AddKeepInfoRet;
import com.ydys.qmb.bean.NameInfo;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.AddKeepInfoPresenterImp;
import com.ydys.qmb.presenter.NameInfoPresenterImp;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.ui.adapter.MyCollectionAdapter;
import com.ydys.qmb.ui.adapter.NameAdapter;
import com.ydys.qmb.ui.custom.LoginDialog;
import com.ydys.qmb.ui.custom.NormalDecoration;
import com.ydys.qmb.ui.custom.PayDialog;
import com.ydys.qmb.util.MyDateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/20.
 */

public class MyCollectionActivity extends BaseActivity implements IBaseView {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.collection_list)
    RecyclerView mCollectionListView;

    @BindView(R.id.spin_kit)
    SpinKitView spinKitView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    MyCollectionAdapter myCollectionAdapter;

    private NameInfoPresenterImp nameInfoPresenterImp;

    AddKeepInfoPresenterImp addKeepInfoPresenterImp;

    private UserInfo mUserInfo;

    private int currentPage = 1;

    private int pageSize = 20;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
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
        mTitleTv.setText("收藏好名");

        View headView = new View(this);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(12)));

        myCollectionAdapter = new MyCollectionAdapter(this, null);
        mCollectionListView.setLayoutManager(new LinearLayoutManager(this));
        mCollectionListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.transparent), SizeUtils.dp2px(12)));
        mCollectionListView.setAdapter(myCollectionAdapter);
        myCollectionAdapter.addHeaderView(headView);


        mCollectionListView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String name = myCollectionAdapter.getData().get(position).getSurName();
                String birthDate = SPUtils.getInstance().getString("birth_date", TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));

                if (!StringUtils.isEmpty(name)) {
                    Intent intent = new Intent(MyCollectionActivity.this, NameDetailActivity.class);
                    intent.putExtra("surname", name.length() > 0 ? name.substring(0, 1) : "");
                    intent.putExtra("name", name.length() > 1 ? name.substring(1) : "");
                    intent.putExtra("name_id", myCollectionAdapter.getData().get(position).getId() + "");
                    intent.putExtra("query_birth_date", birthDate);
                    intent.putExtra("query_sex", SPUtils.getInstance().getInt("query_sex", 1));
                    intent.putExtra("show_type", 1);
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_remove) {
                    QueryNameInfo addParams = new QueryNameInfo();
                    addParams.setUser_id(mUserInfo.getId());
                    addParams.setType(mUserInfo.getLogin_type());
                    addParams.setPhone(mUserInfo.getPhone());
                    addParams.setOpenid(mUserInfo.getOpenid());
                    addParams.setNameId(myCollectionAdapter.getData().get(position).getId());

                    addKeepInfoPresenterImp.addKeep(addParams);
                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });


        myCollectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;

                if (mUserInfo != null) {
                    QueryNameInfo queryNameInfo = new QueryNameInfo();
                    queryNameInfo.setUser_id(mUserInfo.getId());
                    queryNameInfo.setType(mUserInfo.getLogin_type());
                    queryNameInfo.setPhone(mUserInfo.getPhone());
                    queryNameInfo.setOpenid(mUserInfo.getOpenid());
                    queryNameInfo.setPage(currentPage);
                    nameInfoPresenterImp.getCollectionList(queryNameInfo);
                }
            }
        }, mCollectionListView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUserInfo = App.mUserInfo;
        nameInfoPresenterImp = new NameInfoPresenterImp(this, this);
        addKeepInfoPresenterImp = new AddKeepInfoPresenterImp(this, this);

        if (mUserInfo != null) {
            QueryNameInfo queryNameInfo = new QueryNameInfo();
            queryNameInfo.setUser_id(mUserInfo.getId());
            queryNameInfo.setType(mUserInfo.getLogin_type());
            queryNameInfo.setPhone(mUserInfo.getPhone());
            queryNameInfo.setOpenid(mUserInfo.getOpenid());
            queryNameInfo.setPage(currentPage);
            nameInfoPresenterImp.getCollectionList(queryNameInfo);
        }

        refreshData();
    }

    public void refreshData() {
        if (mUserInfo != null) {
            QueryNameInfo queryNameInfo = new QueryNameInfo();
            queryNameInfo.setUser_id(mUserInfo.getId());
            queryNameInfo.setType(mUserInfo.getLogin_type());
            queryNameInfo.setPhone(mUserInfo.getPhone());
            queryNameInfo.setOpenid(mUserInfo.getOpenid());
            queryNameInfo.setPage(currentPage);
            nameInfoPresenterImp.getCollectionList(queryNameInfo);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
        spinKitView.setVisibility(View.GONE);
    }

    @Override
    public void loadDataSuccess(Object tData) {
        spinKitView.setVisibility(View.GONE);

        if (tData != null) {
            if (tData instanceof NameInfoRet && ((NameInfoRet) tData).getCode() == Constants.SUCCESS) {

                if (currentPage == 1) {
                    if (((NameInfoRet) tData).getData().size() > 0) {
                        mNoDataLayout.setVisibility(View.GONE);
                        mCollectionListView.setVisibility(View.VISIBLE);
                        myCollectionAdapter.setNewData(((NameInfoRet) tData).getData());
                    } else {
                        mNoDataLayout.setVisibility(View.VISIBLE);
                        mCollectionListView.setVisibility(View.GONE);
                    }
                } else {
                    myCollectionAdapter.addData(((NameInfoRet) tData).getData());
                }

                if (((NameInfoRet) tData).getData().size() == pageSize) {
                    myCollectionAdapter.loadMoreComplete();
                } else {
                    myCollectionAdapter.loadMoreEnd(true);
                }
            }

            if (tData instanceof AddKeepInfoRet) {
                if (((AddKeepInfoRet) tData).getCode() == Constants.SUCCESS) {
                    refreshData();
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        spinKitView.setVisibility(View.GONE);
        if (currentPage == 1) {
            mNoDataLayout.setVisibility(View.VISIBLE);
            mCollectionListView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
