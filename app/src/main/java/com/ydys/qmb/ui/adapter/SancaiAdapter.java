package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.ScwgInfo;

import java.util.List;


public class SancaiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;


    public SancaiAdapter(Context context, List<String> datas) {
        super(R.layout.sancai_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String temp) {
        holder.setText(R.id.tv_sc_wuxing, temp);
        switch (holder.getAdapterPosition()) {
            case 0:
                holder.setText(R.id.tv_wuxing_title, "[天才]");
                break;
            case 1:
                holder.setText(R.id.tv_wuxing_title, "[地才]");
                break;
            case 2:
                holder.setText(R.id.tv_wuxing_title, "[人才]");
                break;
            default:
                break;
        }

        if (temp.equals("金")) {
            holder.setBackgroundColor(R.id.tv_sc_wuxing, ContextCompat.getColor(mContext, R.color.jin_color));
        } else if (temp.equals("木")) {
            holder.setBackgroundColor(R.id.tv_sc_wuxing, ContextCompat.getColor(mContext, R.color.mu_color));
        } else if (temp.equals("水")) {
            holder.setBackgroundColor(R.id.tv_sc_wuxing, ContextCompat.getColor(mContext, R.color.shui_color));
        } else if (temp.equals("火")) {
            holder.setBackgroundColor(R.id.tv_sc_wuxing, ContextCompat.getColor(mContext, R.color.huo_color));
        } else {
            holder.setBackgroundColor(R.id.tv_sc_wuxing, ContextCompat.getColor(mContext, R.color.tu_color));
        }

    }
}