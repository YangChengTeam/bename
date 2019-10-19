package com.ydys.qmb.ui.adapter;

import android.content.Context;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.FunInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.ui.custom.ScaleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class FunListAdapter extends BaseQuickAdapter<FunInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public FunListAdapter(Context context, List<FunInfo> datas, int tempWidth) {
        super(R.layout.fun_item_view, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, FunInfo funInfo) {
        holder.setText(R.id.tv_gif_name, funInfo.getName());

        ScaleImageView imageView = holder.getView(R.id.iv_fun_img);
        imageView.setInitSize(funInfo.getWidth(), funInfo.getHeight());

        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(Constants.LOCAL_GIF_FUNS_URL + funInfo.getCoverImage())
                .apply(options)
                .into(imageView);
    }
}