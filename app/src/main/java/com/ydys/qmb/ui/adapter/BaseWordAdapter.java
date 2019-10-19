package com.ydys.qmb.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;

import java.util.List;


public class BaseWordAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {

    private Context mContext;

    public BaseWordAdapter(Context context, List<WordInfo> datas) {
        super(R.layout.base_word_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WordInfo temp) {
        holder.setText(R.id.tv_base_word, temp.getWord_name())
                .setText(R.id.tv_ft_word, temp.getTraditional())
                .setText(R.id.tv_pingyin, temp.getPin_yin())
                .setText(R.id.tv_bihua, temp.getBi_hua())
                .setText(R.id.tv_wuxing, temp.getWu_xing())
                .setText(R.id.tv_jixiong, temp.getIs_ji());

        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_double_color));
        }
    }
}