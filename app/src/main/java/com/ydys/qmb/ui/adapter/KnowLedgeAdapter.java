package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.KnowLedge;

import java.util.List;


public class KnowLedgeAdapter extends BaseQuickAdapter<KnowLedge, BaseViewHolder> {

    private Context mContext;

    public KnowLedgeAdapter(Context context, List<KnowLedge> datas) {
        super(R.layout.know_ledge_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, KnowLedge temp) {
        holder.setText(R.id.tv_wuxing, temp.getWuxing())
                .setText(R.id.tv_wuxing_zhu, temp.getWuxingZhu())
                .setText(R.id.tv_wuxing_desc, temp.getWuxingDesc());
    }
}