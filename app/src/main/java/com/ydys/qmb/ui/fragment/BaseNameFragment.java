package com.ydys.qmb.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.NameDetailRet;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.NameDetailPresenterImp;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.adapter.BaseWordAdapter;
import com.ydys.qmb.ui.adapter.CharacterAdapter;
import com.ydys.qmb.ui.adapter.WordMeanAdapter;
import com.ydys.qmb.ui.custom.NormalDecoration;
import com.ydys.qmb.view.NameDetailView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/4/20.
 */
public class BaseNameFragment extends BaseFragment implements NameDetailView {

    @BindView(R.id.layout_base_info)
    LinearLayout mBaseInfoLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.base_word_list)
    RecyclerView mBaseWordListView;

    @BindView(R.id.word_mean_list)
    RecyclerView mWordMeanListView;

    @BindView(R.id.character_list_view)
    RecyclerView mCharacterListView;

    BaseWordAdapter baseWordAdapter;

    WordMeanAdapter wordMeanAdapter;

    CharacterAdapter characterAdapter;

    NameDetailPresenterImp nameDetailPresenterImp;

    private NameDetailActivity activity;

    @Override
    protected int getContentView() {
        return R.layout.fragment_base_name;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        activity = (NameDetailActivity) getActivity();
        nameDetailPresenterImp = new NameDetailPresenterImp(this, activity);

        baseWordAdapter = new BaseWordAdapter(getActivity(), null);
        mBaseWordListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBaseWordListView.setAdapter(baseWordAdapter);

        wordMeanAdapter = new WordMeanAdapter(getActivity(), null);
        mWordMeanListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordMeanListView.addItemDecoration(new NormalDecoration(0, SizeUtils.dp2px(4)));
        mWordMeanListView.setAdapter(wordMeanAdapter);

        characterAdapter = new CharacterAdapter(getActivity(), null);
        mCharacterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCharacterListView.setAdapter(characterAdapter);

        nameDetailPresenterImp.getNameDetail(activity.getQuerySurName(), activity.getQueryName(), App.mUserInfo != null ? App.mUserInfo.getId() : "");
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
    public void loadDataSuccess(NameDetailRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() == null || tData.getData().getWordInfo() == null || tData.getData().getWordInfo().size() == 0) {
                mBaseInfoLayout.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            } else {
                mBaseInfoLayout.setVisibility(View.VISIBLE);
                mNoDataLayout.setVisibility(View.GONE);

                baseWordAdapter.setNewData(tData.getData().getWordInfo());
                wordMeanAdapter.setNewData(tData.getData().getWordInfo());
                characterAdapter.setNewData(tData.getData().getWordInfo());

                if (!StringUtils.isEmpty(tData.getData().getName_id())) {
                    activity.setQueryNameId(tData.getData().getName_id());
                }
                MessageEvent messageEvent = new MessageEvent("base_name_info");
                messageEvent.setNameDetailWrapper(tData.getData());
                EventBus.getDefault().post(messageEvent);
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
