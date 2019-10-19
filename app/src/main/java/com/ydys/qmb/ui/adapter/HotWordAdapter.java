package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.HotWordInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class HotWordAdapter extends BaseQuickAdapter<HotWordInfo, BaseViewHolder> {

    private Context mContext;

    public HotWordAdapter(Context context, List<HotWordInfo> datas) {
        super(R.layout.hot_word_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, HotWordInfo item) {
        holder.setText(R.id.tv_hot_word, item.getHotWord());

    }
}