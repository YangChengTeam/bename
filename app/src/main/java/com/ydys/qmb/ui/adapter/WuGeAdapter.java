package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WuGeInfo;

import java.util.List;


public class WuGeAdapter extends BaseQuickAdapter<WuGeInfo, BaseViewHolder> {

    private Context mContext;

    public WuGeAdapter(Context context, List<WuGeInfo> datas) {
        super(R.layout.wu_ge_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WuGeInfo temp) {
        holder.setText(R.id.tv_wg_title, temp.getWugeTitle())
                .setText(R.id.tv_wg_value, temp.getWugeTitle())
                .setText(R.id.tv_wg_remark, temp.getWugeRemark())
                .setText(R.id.tv_wuxing, temp.getWugeWuxing())
                .setText(R.id.tv_jx, temp.getWugeJiXiong())
                .setText(R.id.tv_wg_desc, temp.getWugeDesc());
    }
}