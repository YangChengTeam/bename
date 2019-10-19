package com.ydys.qmb.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydys.qmb.R;


public class PayDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private TextView mXsTv;

    private TextView mSexTv;

    private TextView mGongliDateTv;

    private TextView mPriceTv;

    private TextView mOldPriceTv;

    private TextView mPayCountTv;

    private LinearLayout mWxPayLayout;

    private LinearLayout mAlipayLayout;

    private ImageView mCloseIv;

    private TextView mPayTitleTv;

    public PayWayListener payWayListener;

    public void setPayWayListener(PayWayListener payWayListener) {
        this.payWayListener = payWayListener;
    }

    public interface PayWayListener {
        void wxpay();

        void alipay();
    }

    public PayDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public void setPayInfo(String xs, int sex, String glDate, double price, double oldPrice,String surname) {
        mXsTv.setText(xs);
        mSexTv.setText(sex == 1 ? "男" : "女");
        mGongliDateTv.setText(glDate);
        mPriceTv.setText(price + "");
        mOldPriceTv.setText(oldPrice + "");
        mPayTitleTv.setText("解锁“" + surname + "”所有高分吉祥美名");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mXsTv = findViewById(R.id.tv_xs);
        mSexTv = findViewById(R.id.tv_sex);
        mGongliDateTv = findViewById(R.id.tv_gongli_date);
        mPriceTv = findViewById(R.id.tv_price);
        mOldPriceTv = findViewById(R.id.tv_old_price);
        mPayCountTv = findViewById(R.id.tv_pay_count);
        mWxPayLayout = findViewById(R.id.layout_wx_pay);
        mAlipayLayout = findViewById(R.id.layout_alipay_pay);
        mPayTitleTv = findViewById(R.id.tv_pay_desc);

        mCloseIv = findViewById(R.id.iv_close);
        mOldPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        setCanceledOnTouchOutside(false);
        mPayCountTv.setText(Html.fromHtml("已有<font color='#ff5555'>234214</font>人获得了高分美名"));
        mCloseIv.setOnClickListener(this);

        mWxPayLayout.setOnClickListener(this);
        mAlipayLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.layout_wx_pay:
                payWayListener.wxpay();
                dismiss();
                break;
            case R.id.layout_alipay_pay:
                payWayListener.alipay();
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void setOnKeyListener(OnKeyListener onKeyListener) {
        super.setOnKeyListener(onKeyListener);
    }
}