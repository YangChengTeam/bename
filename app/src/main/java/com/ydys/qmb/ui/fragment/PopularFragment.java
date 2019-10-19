package com.ydys.qmb.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.orhanobut.logger.Logger;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.PopularInfoRet;
import com.ydys.qmb.bean.TrendInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.PopularInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.util.LineChartManager;
import com.ydys.qmb.view.PopularInfoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogRecord;

import butterknife.BindView;

public class PopularFragment extends BaseFragment implements PopularInfoView {

    @BindView(R.id.layout_popular_base_info)
    LinearLayout mBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.tv_first_name)
    TextView mFirstNameTv;

    @BindView(R.id.tv_last_name)
    TextView mLastNameTv;

    @BindView(R.id.tv_male_per)
    TextView mMalePerTv;

    @BindView(R.id.tv_female_per)
    TextView mFeMalePerTv;

    @BindView(R.id.first_line_chart)
    LineChart firstLineChart;

    @BindView(R.id.layout_last_name_title)
    LinearLayout mLastNameTilteLayout;

    @BindView(R.id.layout_last_name)
    LinearLayout mLastNameLayout;

    @BindView(R.id.last_line_chart)
    LineChart lastLineChart;

    PieChart pieChart;

    PopularInfoPresenterImp popularInfoPresenterImp;

    private ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

    private NameDetailActivity activity;

    private LineChartManager firstLineChartManager;

    private LineChartManager lastLineChartManager;

    private String[] years = {"60年代", "70年代", "80年代", "90年代", "00年代", "2013年", "2014年", "2015年", "2016年", "2017年"};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.fragment_popular;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        activity = (NameDetailActivity) getActivity();
        popularInfoPresenterImp = new PopularInfoPresenterImp(this, getActivity());
        popularInfoPresenterImp.getPopularInfo(activity.getQueryNameId());

        //折线图
        initLineView();

        if (!StringUtils.isEmpty(activity.getQueryName())) {
            if (activity.getQueryName().length() > 0) {
                mFirstNameTv.setText(activity.getQueryName().substring(0, 1) + "字在首位的年代潮流趋势");
            }
            if (activity.getQueryName().length() > 1) {
                mLastNameTilteLayout.setVisibility(View.VISIBLE);
                mLastNameLayout.setVisibility(View.VISIBLE);
                mLastNameTv.setText(activity.getQueryName().substring(1) + "字在末尾的年代潮流趋势");
            }else{
                mLastNameTilteLayout.setVisibility(View.GONE);
                mLastNameLayout.setVisibility(View.GONE);
                mFirstNameTv.setText(activity.getQueryName().substring(0, 1) + "字的年代潮流趋势");
            }
        }
    }

    public void initLineView() {
        firstLineChartManager = new LineChartManager(firstLineChart);
        lastLineChartManager = new LineChartManager(lastLineChart);
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
    public void loadDataSuccess(PopularInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData.getData() == null || StringUtils.isEmpty(tData.getData().getMale_per())) {
                mBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            } else {

                mBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);

                mMalePerTv.setText(tData.getData().getMale_per() + "%");
                mFeMalePerTv.setText(tData.getData().getFemale_per() + "%");

                int maxMale = 0;
                int maxFemale = 0;
                if (tData.getData().getMale() != null && tData.getData().getMale().length > 0) {
                    maxMale = Collections.max(Arrays.asList(tData.getData().getMale()));
                    List<TrendInfo> maleList = new ArrayList<>();
                    for (int i = 0; i < tData.getData().getMale().length; i++) {
                        TrendInfo trendInfo = new TrendInfo();
                        trendInfo.setYears(years[i]);
                        trendInfo.setTrendValue(tData.getData().getMale()[i]);
                        maleList.add(trendInfo);
                    }

                    //展示图表
                    firstLineChartManager.showLineChart(maleList, "男性排名", getResources().getColor(R.color.colorPrimary));
                    firstLineChartManager.setMarkerView(getActivity());
                }

                if (tData.getData().getFemale() != null && tData.getData().getFemale().length > 0) {
                    maxFemale = Collections.max(Arrays.asList(tData.getData().getFemale()));
                    List<TrendInfo> femaleList = new ArrayList<>();
                    for (int i = 0; i < tData.getData().getFemale().length; i++) {
                        TrendInfo trendInfo = new TrendInfo();
                        trendInfo.setYears(years[i]);
                        trendInfo.setTrendValue(tData.getData().getFemale()[i]);
                        femaleList.add(trendInfo);
                    }
                    firstLineChartManager.addLine(femaleList, "女性排名", getResources().getColor(R.color.female_color));
                }

                int lastMax = maxMale > maxFemale ? maxMale : maxFemale;
                Logger.i("lastMax--->" + lastMax);

                firstLineChartManager.setYAxisData(lastMax + 100, 0, 4);

                //末尾
                int secondMaxMale = 0;
                int secondMaxFemale = 0;
                if (tData.getData().getSecond_male() != null && tData.getData().getSecond_male().length > 0) {
                    secondMaxMale = Collections.max(Arrays.asList(tData.getData().getSecond_male()));
                    List<TrendInfo> maleList = new ArrayList<>();
                    for (int i = 0; i < tData.getData().getSecond_male().length; i++) {
                        TrendInfo trendInfo = new TrendInfo();
                        trendInfo.setYears(years[i]);
                        trendInfo.setTrendValue(tData.getData().getSecond_male()[i]);
                        maleList.add(trendInfo);
                    }

                    //展示图表
                    lastLineChartManager.showLineChart(maleList, "男", getResources().getColor(R.color.colorPrimary));
                    lastLineChartManager.setMarkerView(getActivity());
                }

                if (tData.getData().getSecond_female() != null && tData.getData().getSecond_female().length > 0) {
                    secondMaxFemale = Collections.max(Arrays.asList(tData.getData().getSecond_female()));
                    List<TrendInfo> femaleList = new ArrayList<>();
                    for (int i = 0; i < tData.getData().getSecond_female().length; i++) {
                        TrendInfo trendInfo = new TrendInfo();
                        trendInfo.setYears(years[i]);
                        trendInfo.setTrendValue(tData.getData().getSecond_female()[i]);
                        femaleList.add(trendInfo);
                    }
                    lastLineChartManager.addLine(femaleList, "女", getResources().getColor(R.color.female_color));
                }
                int lastSecondMax = secondMaxMale > secondMaxFemale ? secondMaxMale : secondMaxFemale;
                Logger.i("lastSecondMax--->" + lastSecondMax);
                lastLineChartManager.setYAxisData(lastSecondMax + 100, 0, 4);
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
