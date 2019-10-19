package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;
import com.ydys.qmb.ui.custom.ExpandTextView;

import java.util.List;


public class CharacterAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {

    private Context mContext;

    public CharacterAdapter(Context context, List<WordInfo> datas) {
        super(R.layout.character_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WordInfo temp) {

        holder.setText(R.id.tv_base_word, temp.getWord_name())
                .setText(R.id.tv_ft_word, temp.getTraditional())
                .setText(R.id.tv_pingyin, temp.getPin_yin())
                .setText(R.id.tv_bihua, temp.getBi_hua())
                .setText(R.id.tv_wuxing, temp.getWu_xing())
                .setText(R.id.tv_pp, temp.getPian_pang());

        ExpandTextView expandTextView = holder.getView(R.id.tv_expand_character);
        expandTextView.setText(Html.fromHtml(temp.getJibenjieshi()));
    }
}