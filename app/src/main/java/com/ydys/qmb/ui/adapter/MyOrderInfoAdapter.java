package com.ydys.qmb.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.NameInfo;
import com.ydys.qmb.bean.OrderInfo;

import java.util.List;

public class MyOrderInfoAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    private Context mContext;

    public MyOrderInfoAdapter(Context context, List<OrderInfo> datas) {
        super(R.layout.my_order_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderInfo orderInfo) {
        holder.setText(R.id.tv_order_number, orderInfo.getOrderno())
                .setText(R.id.tv_sur_name, orderInfo.getXing())
                .setText(R.id.tv_order_title, "付费功能：" + orderInfo.getTitle());
        if (orderInfo.getUpdate_time() > 0) {
            holder.setText(R.id.tv_order_date, "订单时间：" + TimeUtils.millis2String(orderInfo.getUpdate_time() * 1000));
        }

    }
}