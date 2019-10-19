package com.ydys.qmb.ui.fragment;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.ScwgInfo;
import com.ydys.qmb.bean.ScwgInfoRet;
import com.ydys.qmb.bean.WuGeInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.ScwgInfoPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.adapter.SancaiAdapter;
import com.ydys.qmb.ui.adapter.WuGeAdapter;
import com.ydys.qmb.view.ScwgInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScwgFragment extends BaseFragment implements ScwgInfoView {

    @BindView(R.id.layout_scwg_base_info)
    LinearLayout mBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.sancai_list_view)
    RecyclerView mSanCaiListView;

    @BindView(R.id.wg_list_view)
    RecyclerView mWuGeListView;

    @BindView(R.id.tv_sc_fenxi)
    TextView mSanCaiFenXiTv;

    @BindView(R.id.tv_total_jx)
    TextView mTotalJxTv;

    @BindView(R.id.tv_first_name)
    TextView mFirstNameTv;

    @BindView(R.id.tv_second_name)
    TextView mSecondNameTv;

    @BindView(R.id.tv_three_name)
    TextView mThreeNameTv;

    @BindView(R.id.tv_first_bihua)
    TextView mFirstBiHuaTv;

    @BindView(R.id.tv_second_bihua)
    TextView mSecondBiHuaTv;

    @BindView(R.id.tv_three_bihua)
    TextView mThreeBiHuaTv;

    @BindView(R.id.tv_waige_num)
    TextView mWaiGeNumTv;

    @BindView(R.id.tv_waige_yy)
    TextView mWaiGeYYTv;

    @BindView(R.id.tv_tiange_num)
    TextView mTianGeNumTv;

    @BindView(R.id.tv_tiange_yy)
    TextView mTianGeYYTv;

    @BindView(R.id.tv_dige_num)
    TextView mDiGeNumTv;

    @BindView(R.id.tv_dige_yy)
    TextView mDiGeYYTv;

    @BindView(R.id.tv_renge_num)
    TextView mRenGeNumTv;

    @BindView(R.id.tv_renge_yy)
    TextView mRenGeYYTv;

    @BindView(R.id.tv_zongge_num)
    TextView mZongGeNumTv;

    @BindView(R.id.tv_zongge_yy)
    TextView mZongGeYYTv;

    @BindView(R.id.layout_table)
    RelativeLayout mTableLayout;

    @BindView(R.id.layout_word_info)
    LinearLayout mWordInfoLayout;

    @BindView(R.id.layout_three_word_info)
    LinearLayout mThreeWordInfoLayout;

    @BindView(R.id.tv_two_word_bottom)
    TextView mTwoWordBottomTv;

    SancaiAdapter sancaiAdapter;

    WuGeAdapter wuGeAdapter;

    ScwgInfoPresenterImp scwgInfoPresenterImp;

    ScwgInfo mScwgInfo;

    private String[] wgRemark = {"主掌晚年运势", "主掌中年运势", "主掌祖传运势", "主掌早年运势", "主掌主体运势"};

    private String[] wgTitles = {"总格", "外格", "天格", "地格", "人格"};

    private String[] scTitles = {"总论:", "性格:", "意志:", "事业:", "家庭:", "婚姻:", "子女:", "社交:", "精神:", "财运:", "健康:", "老运:"};

    private NameDetailActivity activity;

    @Override
    protected int getContentView() {
        return R.layout.fragment_scwg;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        activity = (NameDetailActivity) getActivity();

        scwgInfoPresenterImp = new ScwgInfoPresenterImp(this, getActivity());

        sancaiAdapter = new SancaiAdapter(getActivity(), null);
        mSanCaiListView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mSanCaiListView.setAdapter(sancaiAdapter);

        wuGeAdapter = new WuGeAdapter(getActivity(), null);
        mWuGeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWuGeListView.setAdapter(wuGeAdapter);


        //设置五格的表格数据
        if (activity.getWordList() != null) {
            if (activity.getWordList().size() > 0) {
                mFirstNameTv.setText(activity.getWordList().get(0).getWord_name());
                mFirstBiHuaTv.setText(activity.getWordList().get(0).getBi_hua() + "");
            }
            if (activity.getWordList().size() > 1) {
                mSecondNameTv.setText(activity.getWordList().get(1).getWord_name());
                mSecondBiHuaTv.setText(activity.getWordList().get(1).getBi_hua() + "");
            }
            if (activity.getWordList().size() > 2) {
                mThreeNameTv.setText(activity.getWordList().get(2).getWord_name());
                mThreeBiHuaTv.setText(activity.getWordList().get(2).getBi_hua() + "");
                mTableLayout.setBackgroundResource(R.mipmap.three_word_table);
                mThreeWordInfoLayout.setVisibility(View.VISIBLE);
                mTwoWordBottomTv.setVisibility(View.GONE);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SizeUtils.dp2px(66), SizeUtils.dp2px(90));
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                mWordInfoLayout.setLayoutParams(params);

            } else {
                mTableLayout.setBackgroundResource(R.mipmap.two_word_table);
                mThreeWordInfoLayout.setVisibility(View.GONE);
                mTwoWordBottomTv.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SizeUtils.dp2px(66), SizeUtils.dp2px(60));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                mWordInfoLayout.setLayoutParams(params);
            }
        }


        scwgInfoPresenterImp.getScwgInfo(activity.getQueryNameId(), activity.getQuerySurName(), activity.getQueryName());
    }

    @Override
    public void loadData() {
        String scValue = getResources().getString(R.string.sc_value);
        scValue = scValue.replace("\n", "<br>");
        //mSanCaiFenXiTv.setText(Html.fromHtml(scValue));
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
    public void loadDataSuccess(ScwgInfoRet tData) {

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                mBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);

                mScwgInfo = tData.getData();
                List<String> sanCaiList = new ArrayList<>();
                sanCaiList.add(mScwgInfo.getTian_wx());
                sanCaiList.add(mScwgInfo.getDi_wx());
                sanCaiList.add(mScwgInfo.getRen_wx());
                sancaiAdapter.setNewData(sanCaiList);

                //三才详解2、
                if (!StringUtils.isEmpty(mScwgInfo.getJixiong_desc())) {
                    String tempScDesc = mScwgInfo.getJixiong_desc()
                            .replace("1、", "").replace("2、", "").replace("3、", "").replace("4、", "")
                            .replace("5、", "").replace("6、", "").replace("7、", "").replace("8、", "")
                            .replace("9、", "").replace("10、", "").replace("11、", "").replace("12、", "");

                    tempScDesc = tempScDesc.replace("1", "").replace("2", "").replace("3", "").replace("4", "")
                            .replace("5", "").replace("6", "").replace("7", "").replace("8", "")
                            .replace("9", "").replace("10", "").replace("11", "").replace("12", "");


                    for (int i = 0; i < scTitles.length; i++) {
                        if (i > 0) {
                            tempScDesc = tempScDesc.replace(scTitles[i], "<br/><font color='#938858'>" + scTitles[i] + "</font>&nbsp;&nbsp;");
                        } else {
                            tempScDesc = tempScDesc.replace(scTitles[i], "<font color='#938858'>" + scTitles[i] + "</font>&nbsp;&nbsp;");
                        }

                    }

                    mSanCaiFenXiTv.setText(Html.fromHtml(tempScDesc));
                }

                List<WuGeInfo> wugeList = new ArrayList<>();
                for (int i = 0; i < wgTitles.length; i++) {
                    WuGeInfo wuGeInfo = new WuGeInfo();
                    wuGeInfo.setWugeTitle(wgTitles[i]);
                    wuGeInfo.setWugeRemark(wgRemark[i]);
                    switch (i) {
                        case 0:
                            wuGeInfo.setWugeValue(mScwgInfo.getZongge());
                            wuGeInfo.setWugeWuxing(mScwgInfo.getZong_wx());
                            wuGeInfo.setWugeJiXiong(mScwgInfo.getZongge_jx());
                            wuGeInfo.setWugeDesc(mScwgInfo.getZongge_info());
                            mTotalJxTv.setText(mScwgInfo.getZongge_jx());
                            mZongGeNumTv.setText(mScwgInfo.getZongge() + "");
                            mZongGeYYTv.setText("(" + mScwgInfo.getZong_yy() + mScwgInfo.getZong_wx() + ")");
                            break;
                        case 1:
                            wuGeInfo.setWugeValue(mScwgInfo.getWaige());
                            wuGeInfo.setWugeWuxing(mScwgInfo.getWai_wx());
                            wuGeInfo.setWugeJiXiong(mScwgInfo.getWaige_jx());
                            wuGeInfo.setWugeDesc(mScwgInfo.getWaige_info());
                            mWaiGeNumTv.setText(mScwgInfo.getWaige() + "");
                            mWaiGeYYTv.setText("(" + mScwgInfo.getWai_yy() + mScwgInfo.getWai_wx() + ")");
                            break;
                        case 2:
                            wuGeInfo.setWugeValue(mScwgInfo.getTiange());
                            wuGeInfo.setWugeWuxing(mScwgInfo.getTian_wx());
                            wuGeInfo.setWugeJiXiong(mScwgInfo.getTiange_jx());
                            wuGeInfo.setWugeDesc(mScwgInfo.getTiange_info());
                            mTianGeNumTv.setText(mScwgInfo.getTiange() + "");
                            mTianGeYYTv.setText("(" + mScwgInfo.getTian_yy() + mScwgInfo.getTian_wx() + ")");
                            break;
                        case 3:
                            wuGeInfo.setWugeValue(mScwgInfo.getDige());
                            wuGeInfo.setWugeWuxing(mScwgInfo.getDi_wx());
                            wuGeInfo.setWugeJiXiong(mScwgInfo.getDige_jx());
                            wuGeInfo.setWugeDesc(mScwgInfo.getDige_info());
                            mDiGeNumTv.setText(mScwgInfo.getDige() + "");
                            mDiGeYYTv.setText("(" + mScwgInfo.getDi_yy() + mScwgInfo.getDi_wx() + ")");
                            break;
                        case 4:
                            wuGeInfo.setWugeValue(mScwgInfo.getRenge());
                            wuGeInfo.setWugeWuxing(mScwgInfo.getRen_wx());
                            wuGeInfo.setWugeJiXiong(mScwgInfo.getRenge_jx());
                            wuGeInfo.setWugeDesc(mScwgInfo.getRenge_info());

                            mRenGeNumTv.setText(mScwgInfo.getRenge() + "");
                            mRenGeYYTv.setText("(" + mScwgInfo.getRen_yy() + mScwgInfo.getRen_wx() + ")");
                            break;
                        default:
                            break;
                    }
                    wugeList.add(wuGeInfo);
                }
                wuGeAdapter.setNewData(wugeList);
            } else {
                mBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
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
