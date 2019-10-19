package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;

import java.util.List;


public class NameWordAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {

    private Context mContext;

    public NameWordAdapter(Context context, List<WordInfo> datas) {
        super(R.layout.name_word_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WordInfo temp) {
        holder.setText(R.id.tv_pingyin, temp.getPin_yin()).setText(R.id.tv_word, temp.getWord_name()).setText(R.id.tv_wuxing, "[" + temp.getWu_xing() + "]");
    }
}