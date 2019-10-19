package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.StickerType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.core.content.ContextCompat;


public class StickerTypeAdapter extends BaseQuickAdapter<StickerType, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public StickerTypeAdapter(Context context, List<StickerType> datas) {
        super(R.layout.sticker_type_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, StickerType stickerType) {
        Logger.i("head url --->" + stickerType.getTypeName());
        holder.setText(R.id.tv_sticker_type, stickerType.getTypeName());
        if (stickerType.isSelected()) {
            holder.setBackgroundColor(R.id.layout_item_type, ContextCompat.getColor(mContext, R.color.white));
            holder.setVisible(R.id.view_left, true);
        } else {
            holder.setBackgroundColor(R.id.layout_item_type, ContextCompat.getColor(mContext, R.color.bg_color));
            holder.setVisible(R.id.view_left, false);
        }
    }
}