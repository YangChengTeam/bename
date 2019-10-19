package com.ydys.qmb.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.PieEntry;
import com.orhanobut.logger.Logger;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.AgeInfo;
import com.ydys.qmb.bean.ChongMingInfoRet;
import com.ydys.qmb.bean.ShengXiaoInfo;
import com.ydys.qmb.bean.TrendInfo;
import com.ydys.qmb.bean.XingZuoInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.ChongMingInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.custom.ChinaMapView;
import com.ydys.qmb.util.LineChartLevelManager;
import com.ydys.qmb.util.LineChartManager;
import com.ydys.qmb.view.ChongMingInfoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class DuplicateFragment extends BaseFragment implements ChongMingInfoView {

    @BindView(R.id.layout_duplicate_base_info)
    LinearLayout mBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.tv_chongming_no_data)
    TextView mNoDataTv;

    @BindView(R.id.age_line_chart)
    LineChart ageLineChart;

    @BindView(R.id.xingzuo_line_chart)
    LineChart xingzuoLineChart;

    @BindView(R.id.shengxiao_line_chart)
    LineChart shengxiaoLineChart;

    @BindView(R.id.china_map)
    ChinaMapView chinaMapView;

    @BindView(R.id.layout_age_info)
    LinearLayout mAgeInfoLayout;

    @BindView(R.id.layout_xingzuo_info)
    LinearLayout mXingZuoInfoLayout;

    @BindView(R.id.layout_shengxiao_info)
    LinearLayout mShengXiaoInfoLayout;

    @BindView(R.id.iv_no_data)
    ImageView mNoDataIv;

    private LineChartLevelManager ageLineChartManager;

    private LineChartManager xingzuoLineChartManager;

    private LineChartLevelManager shengxiaoLineChartManager;

    private ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

    ChongMingInfoPresenterImp chongMingInfoPresenterImp;

    private NameDetailActivity activity;

    Map<ChinaMapView.Area, Integer> areaDatas;

    private boolean isShow = true;

    Integer[] colorArray = {R.color.common_btn_selected,
            R.color.wx_login_color,
            R.color.qq_login_color,
            R.color.mobile_login_color,
            R.color.low_score_color,
            R.color.tab_select_color,
            R.color.tab_unselect_color,
            R.color.peotry_color,
            R.color.bing_colo1_4,
            R.color.bing_colo1_1,
            R.color.bing_colo1_2,
            R.color.bing_colo1_3};

    @Override
    protected int getContentView() {
        return R.layout.fragment_duplicate;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        ageLineChartManager = new LineChartLevelManager(ageLineChart);
        xingzuoLineChartManager = new LineChartManager(xingzuoLineChart);
        shengxiaoLineChartManager = new LineChartLevelManager(shengxiaoLineChart);

        activity = (NameDetailActivity) getActivity();

        chongMingInfoPresenterImp = new ChongMingInfoPresenterImp(this, getActivity());
        chongMingInfoPresenterImp.getChongMingInfo(activity.getQueryNameId());
        areaDatas = new HashMap<>();

        chinaMapView.setOnProvinceSelectedListener(new ChinaMapView.OnProvinceSelectedListener() {
            @Override
            public void onprovinceSelected(ChinaMapView.Area pArea) {

                if (areaDatas.keySet().contains(pArea)) {
                    Toasty.normal(getActivity(), ChinaMapView.getProvinceNameByArea(pArea) + "人数：" + areaDatas.get(pArea)).show();
                }
            }
        });
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
    public void loadDataSuccess(ChongMingInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData.getData() == null) {
                isShow = false;
            }

            if (tData.getData().getProvince_info() == null || tData.getData().getProvince_info().size() == 0) {
                isShow = false;
            }

            if (tData.getData().getAge_info() == null && tData.getData().getAge_info().size() == 0) {
                isShow = false;
            }

            if (tData.getData().getXingzuo_info() == null && tData.getData().getXingzuo_info().size() == 0) {
                isShow = false;
            }

            if (tData.getData().getShengxiao_info() == null && tData.getData().getShengxiao_info().size() == 0) {
                isShow = false;
            }

            if (!isShow) {
                mBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, ScreenUtils.getScreenHeight() / 2 - SizeUtils.dp2px(168 + 68) - BarUtils.getNavBarHeight(), 0, 0);
                mNoDataIv.setLayoutParams(params);

                mNoDataTv.setText("基于" + AppUtils.getAppName() + "大数据,全国还没有叫" + activity.getQuerySurName() + activity.getQueryName() + "的人，这个名字独一无二");
            } else {
                mBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);
                //省份图
                if (tData.getData().getProvince_info() != null && tData.getData().getProvince_info().size() > 0) {

                    for (int i = 0; i < tData.getData().getProvince_info().size(); i++) {
                        Logger.i("select province name --->" + tData.getData().getProvince_info().get(i).getProvince_name());
                        ChinaMapView.Area area = ChinaMapView.getAreaByProvinceName(tData.getData().getProvince_info().get(i).getProvince_name());
                        if (area != null) {
                            Logger.i("select area --->" + JSON.toJSONString(area));
                            areaDatas.put(area, tData.getData().getProvince_info().get(i).getProvince_num());

                            chinaMapView.setPaintColor(area, ContextCompat.getColor(getActivity(), R.color.color_diy), true);
                        }
                    }
                }

                //设置年龄折线图
                if (tData.getData().getAge_info() != null && tData.getData().getAge_info().size() > 0) {
                    mAgeInfoLayout.setVisibility(View.VISIBLE);
                    setAgeLine(tData.getData().getAge_info());
                } else {
                    mAgeInfoLayout.setVisibility(View.GONE);
                }

                //星座折线图
                if (tData.getData().getXingzuo_info() != null && tData.getData().getXingzuo_info().size() > 0) {
                    mXingZuoInfoLayout.setVisibility(View.VISIBLE);
                    setXingZuoLine(tData.getData().getXingzuo_info());
                } else {
                    mXingZuoInfoLayout.setVisibility(View.GONE);
                }

                //生肖折线图
                if (tData.getData().getShengxiao_info() != null && tData.getData().getShengxiao_info().size() > 0) {
                    mShengXiaoInfoLayout.setVisibility(View.VISIBLE);
                    setShengXiaoLine(tData.getData().getShengxiao_info());
                } else {
                    mShengXiaoInfoLayout.setVisibility(View.GONE);
                }
            }
        } else {
            mBaseInfoLayout.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setAgeLine(List<AgeInfo> age_info) {

        List<Integer> ageValues = new ArrayList<>();
        List<TrendInfo> ageList = new ArrayList<>();
        for (int i = 0; i < age_info.size(); i++) {
            ageValues.add(age_info.get(i).getAge_count());
            TrendInfo trendInfo = new TrendInfo();
            trendInfo.setYears(age_info.get(i).getAge_title());
            trendInfo.setTrendValue(age_info.get(i).getAge_count());
            ageList.add(trendInfo);
        }

        //展示图表
        ageLineChartManager.showLineChart(ageList, "年龄分布", getResources().getColor(R.color.female_color));
        int max = Collections.max(ageValues);
        Logger.i("max--->" + max);

        ageLineChartManager.setYAxisData(max + max > 20 ? 50 : 10, 0, 4);
        ageLineChartManager.setMarkerView(getActivity());
    }


    public void setXingZuoLine(List<XingZuoInfo> xingZuoList) {
        List<Integer> xzValues = new ArrayList<>();
        List<TrendInfo> xzList = new ArrayList<>();
        for (int i = 0; i < xingZuoList.size(); i++) {
            xzValues.add(xingZuoList.get(i).getXingzuo_num());
            TrendInfo trendInfo = new TrendInfo();
            trendInfo.setYears(xingZuoList.get(i).getXingzuo_name());
            trendInfo.setTrendValue(xingZuoList.get(i).getXingzuo_num());
            xzList.add(trendInfo);
        }

        xingzuoLineChartManager.showLineChart(xzList, "星座分布", getResources().getColor(R.color.female_color));
        int max = Collections.max(xzValues);
        Logger.i("max--->" + max);

        xingzuoLineChartManager.setYAxisData(max + max > 20 ? 50 : 10, 0, 4);
        xingzuoLineChartManager.setMarkerView(getActivity());
    }

    public void setShengXiaoLine(List<ShengXiaoInfo> shengXiaoList) {
        List<Integer> sxValues = new ArrayList<>();
        List<TrendInfo> sxList = new ArrayList<>();
        for (int i = 0; i < shengXiaoList.size(); i++) {
            sxValues.add(shengXiaoList.get(i).getShengxiao_num());
            TrendInfo trendInfo = new TrendInfo();
            trendInfo.setYears(shengXiaoList.get(i).getShengxiao_name());
            trendInfo.setTrendValue(shengXiaoList.get(i).getShengxiao_num());
            sxList.add(trendInfo);
        }

        shengxiaoLineChartManager.showLineChart(sxList, "生肖分布", getResources().getColor(R.color.female_color));
        int max = Collections.max(sxValues);
        Logger.i("max--->" + max);

        shengxiaoLineChartManager.setYAxisData(max + max > 20 ? 50 : 10, 0, 4);
        shengxiaoLineChartManager.setMarkerView(getActivity());
    }

    @Override
    public void loadDataError(Throwable throwable) {
        mBaseInfoLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }
}
