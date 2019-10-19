package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.HotGifCollection;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.ui.custom.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class GifCollectionAdapter extends BaseQuickAdapter<HotGifCollection, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public GifCollectionAdapter(Context context, List<HotGifCollection> datas, int tempWidth) {
        super(R.layout.hot_collection_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, HotGifCollection item) {
        holder.setText(R.id.tv_collection_name, item.getCollectionName());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.default_icon);
        options.transform(new GlideRoundTransform(mContext, 6));
        Glide.with(mContext)
                .load(Constants.LOCAL_GIF_URL + item.getCover())
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_hot_collection));
    }
}