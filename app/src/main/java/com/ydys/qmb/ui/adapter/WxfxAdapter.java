package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WxfxShowInfo;

import java.util.List;


public class WxfxAdapter extends BaseQuickAdapter<WxfxShowInfo, BaseViewHolder> {

    private Context mContext;

    public WxfxAdapter(Context context, List<WxfxShowInfo> datas) {
        super(R.layout.wxfx_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WxfxShowInfo temp) {
        holder.setText(R.id.tv_wuxing, temp.getWuxingName())
                .setText(R.id.tv_wuxing_percent, temp.getWuxingPercent());
        ProgressBar progressBar = holder.getView(R.id.pb_wuxing);
        progressBar.setProgress(temp.getWuxingValue());
        switch (holder.getAdapterPosition()) {
            case 0:
                progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_wx_jin_bg));
                holder.setBackgroundRes(R.id.layout_wuxing, R.drawable.wx_jin_round);
                holder.setTextColor(R.id.tv_wuxing_percent,ContextCompat.getColor(mContext,R.color.jin_color));
                break;
            case 1:
                progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_wx_mu_bg));
                holder.setBackgroundRes(R.id.layout_wuxing, R.drawable.wx_mu_round);
                holder.setTextColor(R.id.tv_wuxing_percent,ContextCompat.getColor(mContext,R.color.mu_color));
                break;
            case 2:
                progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_wx_shui_bg));
                holder.setBackgroundRes(R.id.layout_wuxing, R.drawable.wx_shui_round);
                holder.setTextColor(R.id.tv_wuxing_percent,ContextCompat.getColor(mContext,R.color.shui_color));
                break;
            case 3:
                progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_wx_huo_bg));
                holder.setBackgroundRes(R.id.layout_wuxing, R.drawable.wx_huo_round);
                holder.setTextColor(R.id.tv_wuxing_percent,ContextCompat.getColor(mContext,R.color.huo_color));
                break;
            case 4:
                progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_wx_tu_bg));
                holder.setBackgroundRes(R.id.layout_wuxing, R.drawable.wx_tu_round);
                holder.setTextColor(R.id.tv_wuxing_percent,ContextCompat.getColor(mContext,R.color.tu_color));
                break;
            default:
                break;
        }
    }
}