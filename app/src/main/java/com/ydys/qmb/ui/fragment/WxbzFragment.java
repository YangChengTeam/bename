package com.ydys.qmb.ui.fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.umeng.commonsdk.debug.W;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.KnowLedge;
import com.ydys.qmb.bean.WxbzInfo;
import com.ydys.qmb.bean.WxbzInfoRet;
import com.ydys.qmb.bean.WxbzLifeChart;
import com.ydys.qmb.bean.WxfxShowInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.WxbzInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.adapter.ContentWxbzAdapter;
import com.ydys.qmb.ui.adapter.KnowLedgeAdapter;
import com.ydys.qmb.ui.adapter.LeftTypeAdapter;
import com.ydys.qmb.ui.adapter.SimilarAdapter;
import com.ydys.qmb.ui.adapter.WxfxAdapter;
import com.ydys.qmb.ui.custom.NormalDecoration;
import com.ydys.qmb.util.MyDateUtils;
import com.ydys.qmb.view.WxbzInfoView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WxbzFragment extends BaseFragment implements WxbzInfoView {

    @BindView(R.id.layout_wxbz_info)
    LinearLayout mWxbzBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.layout_table_view)
    LinearLayout mWxbzLayout;

    @BindView(R.id.left_type_list_view)
    RecyclerView mLeftTypeListView;

    @BindView(R.id.content_list_view)
    RecyclerView mContentListView;

    @BindView(R.id.wxfx_list_view)
    RecyclerView mWxfxListView;

    @BindView(R.id.similar_list_view)
    RecyclerView mSimilarListView;

    @BindView(R.id.know_ledge_list_view)
    RecyclerView mKnowLedgeListView;

    @BindView(R.id.tv_name)
    TextView mNameTv;

    @BindView(R.id.tv_sex)
    TextView mSexTv;

    @BindView(R.id.tv_shengxiao)
    TextView mShengXiaoTv;

    @BindView(R.id.tv_xingzuo)
    TextView mXingZuoTv;

    @BindView(R.id.tv_gongli_date)
    TextView mGongLiDateTv;

    @BindView(R.id.tv_nongli_date)
    TextView mNongLiDateTv;

    @BindView(R.id.tv_xiyongshen)
    TextView mXysTv;

    @BindView(R.id.tv_xys_desc)
    TextView mXysDescTv;

    TextView mWuXingNumberTv;

    LeftTypeAdapter leftTypeAdapter;

    ContentWxbzAdapter contentWxbzAdapter;

    WxfxAdapter wxfxAdapter;

    SimilarAdapter similarAdapter;

    KnowLedgeAdapter knowLedgeAdapter;

    WxbzInfoPresenterImp wxbzInfoPresenterImp;

    private NameDetailActivity activity;

    List<WxbzLifeChart> wxbzLifeChartList;

    List<String> similarList;

    String[] wuxing = {"金", "木", "水", "火", "土"};

    int[] wuxing_zhu = {R.string.jin_zhu, R.string.mu_zhu, R.string.shui_zhu, R.string.huo_zhu, R.string.tu_zhu};

    int[] wuxing_desc = {R.string.jin_desc, R.string.mu_desc, R.string.shui_desc, R.string.huo_desc, R.string.tu_desc};

    double scale = 0.16;

    @Override
    protected int getContentView() {
        return R.layout.fragment_wxbz;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        wxbzLifeChartList = new ArrayList<>();
        similarList = new ArrayList<>();

        activity = (NameDetailActivity) getActivity();
        wxbzInfoPresenterImp = new WxbzInfoPresenterImp(this, getActivity());

        String[] lefts = {"", "十神", "天干", "地支", "藏干", "五行", "纳音", "个数"};
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < lefts.length; i++) {
            temp.add(lefts[i]);
        }

        leftTypeAdapter = new LeftTypeAdapter(getActivity(), temp);
        mLeftTypeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLeftTypeListView.setAdapter(leftTypeAdapter);

        View tableHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.wxbz_table_head_view, null);
        View tableFootView = LayoutInflater.from(getActivity()).inflate(R.layout.wxbz_table_foot_view, null);
        mWuXingNumberTv = tableFootView.findViewById(R.id.tv_wuxing_number);

        contentWxbzAdapter = new ContentWxbzAdapter(getActivity(), null);
        mContentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentListView.setAdapter(contentWxbzAdapter);
        contentWxbzAdapter.addHeaderView(tableHeadView);
        contentWxbzAdapter.addFooterView(tableFootView);

        List<KnowLedge> knowLedgeList = new ArrayList<>();
        for (int i = 0; i < wuxing.length; i++) {
            KnowLedge knowLedge = new KnowLedge();
            knowLedge.setWuxing(wuxing[i]);
            knowLedge.setWuxingZhu(activity.getResources().getString((wuxing_zhu[i])));
            knowLedge.setWuxingDesc(activity.getResources().getString((wuxing_desc[i])));
            knowLedgeList.add(knowLedge);
        }

        wxfxAdapter = new WxfxAdapter(getActivity(), null);
        mWxfxListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWxfxListView.addItemDecoration(new NormalDecoration(0, SizeUtils.dp2px(4)));
        mWxfxListView.setAdapter(wxfxAdapter);

        similarAdapter = new SimilarAdapter(getActivity(), null);
        mSimilarListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimilarListView.addItemDecoration(new NormalDecoration(0, SizeUtils.dp2px(4)));
        mSimilarListView.setAdapter(similarAdapter);

        knowLedgeAdapter = new KnowLedgeAdapter(getActivity(), knowLedgeList);
        mKnowLedgeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mKnowLedgeListView.setAdapter(knowLedgeAdapter);

        int[] dataInfo = MyDateUtils.getDateInfo(activity.getQueryBirthDate());

        wxbzInfoPresenterImp.getWxbzInfo(activity.getQuerySurName(), activity.getQueryName(), dataInfo[0], dataInfo[1], dataInfo[2], dataInfo[3]);
    }

    @Override
    public void loadData() {

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
    public void loadDataSuccess(WxbzInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            Logger.i(JSON.toJSONString(tData));

            if (tData.getData() != null) {
                mWxbzBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);
                WxbzInfo wxbzInfo = tData.getData();

                if (wxbzInfo.getWu_xing() != null && wxbzInfo.getWu_xing().length > 0) {
                    List<WxfxShowInfo> list = new ArrayList<>();
                    for (int i = 0; i < wxbzInfo.getWu_xing().length; i++) {
                        WxfxShowInfo wxfxShowInfo = new WxfxShowInfo();
                        wxfxShowInfo.setWuxingName(wuxing[i]);
                        double temp = scale * wxbzInfo.getWu_xing()[i];
                        wxfxShowInfo.setWuxingValue((int) (temp * 100));
                        wxfxShowInfo.setWuxingPercent((int) (temp * 100) + "%");
                        list.add(wxfxShowInfo);
                    }
                    wxfxAdapter.setNewData(list);
                }

                if (wxbzInfo.getShishen_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getShishen_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getShishen_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getShishen_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getShishen_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                if (wxbzInfo.getGan_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getGan_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getGan_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getGan_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getGan_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                if (wxbzInfo.getZhi_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getZhi_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getZhi_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getZhi_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getZhi_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                if (wxbzInfo.getCanggan_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getCanggan_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getCanggan_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getCanggan_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getCanggan_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                if (wxbzInfo.getWuxing_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getWuxing_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getWuxing_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getWuxing_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getWuxing_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                if (wxbzInfo.getNayin_info() != null) {
                    WxbzLifeChart wxbzLifeChart = new WxbzLifeChart();
                    wxbzLifeChart.setYearPillar(wxbzInfo.getNayin_info()[0]);
                    wxbzLifeChart.setMonthPillar(wxbzInfo.getNayin_info()[1]);
                    wxbzLifeChart.setDayPillar(wxbzInfo.getNayin_info()[2]);
                    wxbzLifeChart.setHourPillar(wxbzInfo.getNayin_info()[3]);
                    wxbzLifeChartList.add(wxbzLifeChart);
                }

                contentWxbzAdapter.setNewData(wxbzLifeChartList);

                if (!StringUtils.isEmpty(wxbzInfo.getWuxing_detail())) {
                    mWuXingNumberTv.setText(wxbzInfo.getWuxing_detail());
                }

                if (wxbzInfo.getSame() != null) {
                    similarList.add("同类五行：" + wxbzInfo.getSame());
                    similarList.add("异类五行：" + wxbzInfo.getDiff());
                    similarList.add("同类总分：" + wxbzInfo.getSame_all());
                    similarList.add("异类总分：" + wxbzInfo.getDiff_all());
                    similarList.add("综合分析：<font color='#b80002'>八字身弱，八字喜</font><font color='#bf9d00'> [" + wxbzInfo.getGladgod() + "] </font>");
                }

                similarAdapter.setNewData(similarList);
                String tempXysDesc = getResources().getString(R.string.xys_desc);
                tempXysDesc = tempXysDesc.replace("@@","<br/><br/>").replace("##","<font color='#bf9d00'>「" + wxbzInfo.getGladgod() + "」</font>");
                tempXysDesc = tempXysDesc.replace("$","<br/><font color='#918655'>").replace("%%","</font>");
                tempXysDesc = tempXysDesc.replace("A1","<br/><font color='#000000'>").replace("A2","</font>");
                mXysDescTv.setText(Html.fromHtml(tempXysDesc));


                mNameTv.setText(activity.getQuerySurName() + activity.getQueryName());
                mSexTv.setText(activity.getQuerySex() == 0 ? "女" : "男");
                mShengXiaoTv.setText(wxbzInfo.getShengxiao());
                mXingZuoTv.setText(wxbzInfo.getXingzuo());
                mGongLiDateTv.setText(activity.getQueryBirthDate());
                mNongLiDateTv.setText(wxbzInfo.getLunar_year_chinese() + "年" + wxbzInfo.getLunar_month_chinese() + wxbzInfo.getLunar_day_chinese() + " " + wxbzInfo.getLunar_hour_chinese());
                mXysTv.setText(wxbzInfo.getGladgod());
            } else {
                mWxbzBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            }
        } else {
            mWxbzBaseInfoLayout.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        mWxbzBaseInfoLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }
}
