package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.GifInfo;
import com.ydys.qmb.common.Constants;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class GifShowAdapter extends BaseQuickAdapter<GifInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public GifShowAdapter(Context context, List<GifInfo> datas, int tempWidth) {
        super(R.layout.gif_show_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, GifInfo gifInfo) {
        RequestOptions options = new RequestOptions();
        options.override(tempWidth, tempWidth);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        String tempUrl = StringUtils.isEmpty(gifInfo.getRealImgUrl()) ? Constants.LOCAL_GIF_URL + gifInfo.getGifUrl():gifInfo.getRealImgUrl();
        Glide.with(mContext).load(tempUrl).apply(options).into((ImageView) holder.getView(R.id.iv_gif));
    }
}