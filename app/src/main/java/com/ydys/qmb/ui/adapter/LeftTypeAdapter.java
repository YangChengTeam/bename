package com.ydys.qmb.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;

import java.util.List;


public class LeftTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public LeftTypeAdapter(Context context, List<String> datas) {
        super(R.layout.left_type_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String temp) {
        holder.setText(R.id.tv_type_value, temp);
        if(holder.getAdapterPosition() == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }else{
            if (holder.getAdapterPosition() % 2 == 0) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_color));
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_double_color));
            }
        }

    }
}