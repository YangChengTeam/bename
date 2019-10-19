package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ydys.qmb.R;
import com.ydys.qmb.bean.HeadInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.ui.custom.RoundedCornersTransformation;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;


public class HeadInfoAdapter extends BaseQuickAdapter<HeadInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public HeadInfoAdapter(Context context, List<HeadInfo> datas, int tempWidth) {
        super(R.layout.head_item_view, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, HeadInfo headInfo) {

        Logger.i("head url --->" + headInfo.getHeadUrl() + "tempWidth--->" + tempWidth);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        holder.getView(R.id.layout_head_item).setLayoutParams(itemParams);
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.image_def)
                .override(tempWidth - SizeUtils.dp2px(6), tempWidth - SizeUtils.dp2px(6))
                .transform(new RoundedCornersTransformation(SizeUtils.dp2px(6), 0));

        String tempUrl = StringUtils.isEmpty(headInfo.getRealUrl()) ? Constants.BASE_IMAGE_URL + headInfo.getHeadUrl():headInfo.getRealUrl();
        Glide.with(mContext)
                .load(tempUrl)
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_head));
    }
}