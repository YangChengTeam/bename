package com.ydys.qmb.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;
import com.ydys.qmb.bean.WxbzLifeChart;

import java.util.List;


public class ContentWxbzAdapter extends BaseQuickAdapter<WxbzLifeChart, BaseViewHolder> {

    private Context mContext;

    public ContentWxbzAdapter(Context context, List<WxbzLifeChart> datas) {
        super(R.layout.content_wxbz_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WxbzLifeChart temp) {
        holder.setText(R.id.tv_year_column, temp.getYearPillar())
                .setText(R.id.tv_month_column, temp.getMonthPillar())
                .setText(R.id.tv_day_column, temp.getDayPillar())
                .setText(R.id.tv_hour_column, temp.getHourPillar());

        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_item_double_color));
        }
    }
}