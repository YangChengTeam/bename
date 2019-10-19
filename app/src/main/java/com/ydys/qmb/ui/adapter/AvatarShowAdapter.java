package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

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

import java.util.List;


public class AvatarShowAdapter extends BaseQuickAdapter<HeadInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public AvatarShowAdapter(Context context, List<HeadInfo> datas, int tempWidth) {
        super(R.layout.avatar_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, HeadInfo headInfo) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.default_icon);
        options.transform(new RoundedCornersTransformation(SizeUtils.dp2px(6), 0));
        options.override(tempWidth, tempWidth);

        String tempUrl = StringUtils.isEmpty(headInfo.getRealUrl()) ? Constants.BASE_IMAGE_URL + headInfo.getHeadUrl():headInfo.getRealUrl();

        Glide.with(mContext).load(tempUrl).apply(options).into((ImageView) holder.getView(R.id.iv_avatar));
    }
}