package com.ydys.qmb.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.StructureTableInfo;

import java.util.List;


public class ContentStructureAdapter extends BaseQuickAdapter<StructureTableInfo, BaseViewHolder> {

    private Context mContext;

    public ContentStructureAdapter(Context context, List<StructureTableInfo> datas) {
        super(R.layout.content_structure_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, StructureTableInfo temp) {
        holder.setText(R.id.tv_left_title, temp.getLeftTitle())
                .setText(R.id.tv_first_column, temp.getFirstValue())
                .setText(R.id.tv_second_column, temp.getSecondValue())
                .setText(R.id.tv_three_column, temp.getThreeValue());

        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_double_color));
        }
    }
}