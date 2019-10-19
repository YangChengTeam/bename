package com.ydys.qmb.ui.fragment;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.StructureInfo;
import com.ydys.qmb.bean.StructureInfoRet;
import com.ydys.qmb.bean.StructureTableInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.StructureInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.adapter.ContentStructureAdapter;
import com.ydys.qmb.view.StructureInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StructureFragment extends BaseFragment implements StructureInfoView {

    @BindView(R.id.layout_structure_base_info)
    LinearLayout mBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.content_list_view)
    RecyclerView mContentListView;

    @BindView(R.id.tv_dapei_top)
    TextView mDaPeiTopTv;

    @BindView(R.id.tv_dapei_bottom)
    TextView mDaPeiBottomTv;

    @BindView(R.id.tv_bihua_top)
    TextView mBiHuaTopTv;

    @BindView(R.id.tv_bihua_bottom)
    TextView mBiHuaBottomTv;

    @BindView(R.id.tv_total_desc)
    TextView mTotalDescTv;

    @BindView(R.id.layout_dapei)
    LinearLayout mDaPeiLayout;

    @BindView(R.id.layout_bihua)
    LinearLayout mBiHuaLayout;

    @BindView(R.id.layout_total)
    LinearLayout mTotalLayout;

    ContentStructureAdapter contentStructureAdapter;

    private NameDetailActivity activity;

    private StructureInfoPresenterImp structureInfoPresenterImp;

    List<StructureTableInfo> contentList;

    String[] lefts = {"汉字(繁)", "部首", "结构", "构造", "笔画", "笔顺"};

    @Override
    protected int getContentView() {
        return R.layout.fragment_structure;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        contentList = new ArrayList<>();
        structureInfoPresenterImp = new StructureInfoPresenterImp(this, activity);
        activity = (NameDetailActivity) getActivity();

        contentStructureAdapter = new ContentStructureAdapter(getActivity(), null);
        mContentListView.setLayoutManager(new LinearLayoutManager(activity));
        mContentListView.setAdapter(contentStructureAdapter);
    }

    @Override
    public void loadData() {
        structureInfoPresenterImp.getStructureInfo(activity.getQueryNameId(), activity.getQuerySurName(), activity.getQueryName());
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
    public void loadDataSuccess(StructureInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            Logger.i(JSON.toJSONString(tData));
            if (tData.getData() == null || tData.getData().getWord_name() == null && tData.getData().getWord_name().length == 0) {
                mBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            } else {
                mBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);

                StructureInfo structureInfo = tData.getData();
                if (structureInfo.getWord_name() != null && structureInfo.getWord_name().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[0]);
                    structureTableInfo.setFirstValue(structureInfo.getWord_name().length > 0 ? structureInfo.getWord_name()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getWord_name().length > 1 ? structureInfo.getWord_name()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getWord_name().length > 2 ? structureInfo.getWord_name()[2] : "");
                    contentList.add(structureTableInfo);
                }

                if (structureInfo.getPian_pang() != null && structureInfo.getPian_pang().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[1]);
                    structureTableInfo.setFirstValue(structureInfo.getPian_pang().length > 0 ? structureInfo.getPian_pang()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getPian_pang().length > 1 ? structureInfo.getPian_pang()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getPian_pang().length > 2 ? structureInfo.getPian_pang()[2] : "");
                    contentList.add(structureTableInfo);
                }

                if (structureInfo.getJie_gou() != null && structureInfo.getJie_gou().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[2]);
                    structureTableInfo.setFirstValue(structureInfo.getJie_gou().length > 0 ? structureInfo.getJie_gou()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getJie_gou().length > 1 ? structureInfo.getJie_gou()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getJie_gou().length > 2 ? structureInfo.getJie_gou()[2] : "");
                    contentList.add(structureTableInfo);
                }

                if (structureInfo.getGou_zao() != null && structureInfo.getGou_zao().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[3]);
                    structureTableInfo.setFirstValue(structureInfo.getGou_zao().length > 0 ? structureInfo.getGou_zao()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getGou_zao().length > 1 ? structureInfo.getGou_zao()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getGou_zao().length > 2 ? structureInfo.getGou_zao()[2] : "");
                    contentList.add(structureTableInfo);
                }

                if (structureInfo.getBi_hua() != null && structureInfo.getBi_hua().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[4]);
                    structureTableInfo.setFirstValue(structureInfo.getBi_hua().length > 0 ? structureInfo.getBi_hua()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getBi_hua().length > 1 ? structureInfo.getBi_hua()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getBi_hua().length > 2 ? structureInfo.getBi_hua()[2] : "");
                    contentList.add(structureTableInfo);
                }

                if (structureInfo.getBi_shun() != null && structureInfo.getBi_shun().length > 0) {
                    StructureTableInfo structureTableInfo = new StructureTableInfo();
                    structureTableInfo.setLeftTitle(lefts[5]);
                    structureTableInfo.setFirstValue(structureInfo.getBi_shun().length > 0 ? structureInfo.getBi_shun()[0] : "");
                    structureTableInfo.setSecondValue(structureInfo.getBi_shun().length > 1 ? structureInfo.getBi_shun()[1] : "");
                    structureTableInfo.setThreeValue(structureInfo.getBi_shun().length > 2 ? structureInfo.getBi_shun()[2] : "");
                    contentList.add(structureTableInfo);
                }

                contentStructureAdapter.setNewData(contentList);

                if (!StringUtils.isEmpty(tData.getData().getZi_xing_desc())) {
                    mDaPeiLayout.setVisibility(View.VISIBLE);
                    mDaPeiTopTv.setText(Html.fromHtml(tData.getData().getZi_xing_desc()));
                } else {
                    mDaPeiLayout.setVisibility(View.GONE);
                }

                if (!StringUtils.isEmpty(tData.getData().getZi_xing_title())) {
                    mDaPeiBottomTv.setText(Html.fromHtml(tData.getData().getZi_xing_title()));
                }

                if (!StringUtils.isEmpty(tData.getData().getBihua_desc())) {
                    mBiHuaLayout.setVisibility(View.VISIBLE);
                    mBiHuaTopTv.setText(Html.fromHtml(tData.getData().getBihua_desc()));
                } else {
                    mBiHuaLayout.setVisibility(View.GONE);
                }

                if (!StringUtils.isEmpty(tData.getData().getBihua_title())) {
                    mBiHuaBottomTv.setText(Html.fromHtml(tData.getData().getBihua_title()));
                }

                if (!StringUtils.isEmpty(tData.getData().getTotal_desc())) {
                    mTotalLayout.setVisibility(View.VISIBLE);
                    mTotalDescTv.setText(Html.fromHtml(tData.getData().getTotal_desc()));
                } else {
                    mTotalLayout.setVisibility(View.GONE);
                }
            }
        } else {
            mBaseInfoLayout.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        mBaseInfoLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }
}
