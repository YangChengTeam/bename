package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;

import java.util.List;


public class SimilarAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public SimilarAdapter(Context context, List<String> datas) {
        super(R.layout.similar_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String temp) {
        TextView mSimilarDescTv = holder.getView(R.id.tv_similar_desc);
        mSimilarDescTv.setText(Html.fromHtml(temp));
    }
}