package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.PendantInfo;
import com.ydys.qmb.common.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;


public class StickerInfoAdapter extends BaseQuickAdapter<PendantInfo, BaseViewHolder> {

    private Context mContext;

    private int tempHeight;

    public StickerInfoAdapter(Context context, List<PendantInfo> datas, int tempHeight) {
        super(R.layout.sticker_info_view, datas);
        this.mContext = context;
        this.tempHeight = tempHeight;
    }

    @Override
    protected void convert(BaseViewHolder holder, PendantInfo pendantInfo) {
        Logger.i("sticker url --->" + pendantInfo.getImageUrl());

        LinearLayout itemLayout = holder.getView(R.id.layout_sticker_item);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeight));
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(Constants.LOCAL_PENDANT_URL + pendantInfo.getImageUrl())
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_sticker));
    }
}