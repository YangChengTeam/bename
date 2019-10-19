package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;

import java.util.List;


public class WordMeanAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {

    private Context mContext;

    public WordMeanAdapter(Context context, List<WordInfo> datas) {
        super(R.layout.word_mean_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WordInfo temp) {
        LinearLayout meanLayout = holder.getView(R.id.layout_mean);
        if(!StringUtils.isEmpty(temp.getXing_ming_xue())){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            meanLayout.setLayoutParams(params);
            meanLayout.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_xingmingxue, temp.getWord_name() + "：" + temp.getXing_ming_xue());
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            meanLayout.setLayoutParams(params);
            meanLayout.setVisibility(View.GONE);
        }
    }
}