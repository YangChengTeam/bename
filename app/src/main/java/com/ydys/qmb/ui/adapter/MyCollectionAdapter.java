package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.NameInfo;
import com.ydys.qmb.bean.WordInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCollectionAdapter extends BaseQuickAdapter<NameInfo, BaseViewHolder> {

    private Context mContext;

    DecimalFormat df = new DecimalFormat("#0.00");

    public MyCollectionAdapter(Context context, List<NameInfo> datas) {
        super(R.layout.collection_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, NameInfo nameInfo) {

        RecyclerView wordListView = holder.getView(R.id.name_word_list);

        wordListView.setLayoutManager(new GridLayoutManager(mContext, nameInfo.getWordList().size()));
        NameWordAdapter nameWordAdapter = new NameWordAdapter(mContext, nameInfo.getWordList());
        wordListView.setAdapter(nameWordAdapter);

        holder.setText(R.id.tv_score, df.format(nameInfo.getWugeScore()) + "");

        holder.addOnClickListener(R.id.btn_remove);
    }
}