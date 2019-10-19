package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.NameInfo;
import com.ydys.qmb.bean.WordInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NameAdapter extends BaseQuickAdapter<NameInfo, BaseViewHolder> {

    private Context mContext;

    private boolean isBuyVip;

    public void setBuyVip(boolean buyVip) {
        isBuyVip = buyVip;
    }

    public boolean isBuyVip() {
        return isBuyVip;
    }

    DecimalFormat df = new DecimalFormat("#0.00");

    public NameAdapter(Context context, List<NameInfo> datas) {
        super(R.layout.name_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, NameInfo nameInfo) {
        LinearLayout blurLayout = holder.getView(R.id.layout_blur);
        if (!isBuyVip) {
            blurLayout.setVisibility(holder.getAdapterPosition() > 2 ? View.VISIBLE : View.GONE);
        } else {
            blurLayout.setVisibility(View.GONE);
        }

        RecyclerView wordListView = holder.getView(R.id.name_word_list);
        wordListView.setLayoutManager(new GridLayoutManager(mContext, nameInfo.getWordList().size()));
        NameWordAdapter nameWordAdapter = new NameWordAdapter(mContext, nameInfo.getWordList());
        wordListView.setAdapter(nameWordAdapter);

        RecyclerView wordExplainListView = holder.getView(R.id.name_explain_list);
        wordExplainListView.setLayoutManager(new LinearLayoutManager(mContext));
        NameExplainAdapter nameExplainAdapter = new NameExplainAdapter(mContext, nameInfo.getWordList());
        wordExplainListView.setAdapter(nameExplainAdapter);

        holder.setBackgroundRes(R.id.btn_add_keep, nameInfo.getIsCollect() == 0 ? R.drawable.is_not_collection_bg : R.drawable.is_collection_bg);
        holder.setText(R.id.btn_add_keep, nameInfo.getIsCollect() == 0 ? "收藏" : "已收藏");
        holder.setTextColor(R.id.btn_add_keep, ContextCompat.getColor(mContext, nameInfo.getIsCollect() == 0 ? R.color.zonghe_color : R.color.colorPrimary));
        holder.addOnClickListener(R.id.btn_add_keep).addOnClickListener(R.id.layout_blur);
        holder.setText(R.id.tv_name_score, df.format(nameInfo.getWugeScore()) + "");
    }
}