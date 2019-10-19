package com.ydys.qmb.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.ydys.qmb.R;
import com.ydys.qmb.util.MyDateUtils;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CustomDateDialog extends Dialog {

    private Context mContext;

    WheelView mYearWheelView;

    WheelView mMonthWheelView;

    WheelView mTimeDayWheelView;

    WheelView mHourWheelView;

    WheelView mMinuteWheelView;

    Button mConfigBtn;

    public DateSelectListener dateSelectListener;

    public interface DateSelectListener {
        void selectBirDate(String selectDate);
    }

    public void setDateSelectListener(DateSelectListener dateSelectListener) {
        this.dateSelectListener = dateSelectListener;
    }

    public CustomDateDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomDateDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_date_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth();
        params.height = SizeUtils.dp2px(276);
        getWindow().setAttributes(params);
    }

    private void initView() {
        mConfigBtn = findViewById(R.id.btn_config);

        WheelView.WheelViewStyle wheelViewStyle = new WheelView.WheelViewStyle();
        wheelViewStyle.textSize = 16;
        wheelViewStyle.selectedTextSize = 16;
        wheelViewStyle.holoBorderColor = ContextCompat.getColor(mContext, R.color.line_color);
        wheelViewStyle.selectedTextColor = ContextCompat.getColor(mContext, R.color.common_btn_selected);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        //年份
        List<String> yearList = new ArrayList<>();
        int tempSelection = 0;
        for (int i = 1949; i <= 2049; i++) {
            yearList.add(i + "");
        }
        mYearWheelView = findViewById(R.id.wheel_view_year);
        mYearWheelView.setLoop(true);
        mYearWheelView.setStyle(wheelViewStyle);
        mYearWheelView.setSelection(year - 1949);
        mYearWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mYearWheelView.setSkin(WheelView.Skin.Holo);
        mYearWheelView.setWheelData(yearList);

        //月份
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.format("%02d", i));
        }
        mMonthWheelView = findViewById(R.id.wheel_view_month);
        mMonthWheelView.setLoop(true);
        mMonthWheelView.setStyle(wheelViewStyle);
        mMonthWheelView.setSelection(MyDateUtils.getCurrentMonth());
        mMonthWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMonthWheelView.setSkin(WheelView.Skin.Holo);
        mMonthWheelView.setWheelData(list);

        //日,此处暂时不根据月份来确定日期
        List<String> dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(String.format("%02d", i));
        }

        mTimeDayWheelView = findViewById(R.id.wheel_view_day_time);
        mTimeDayWheelView.setLoop(true);
        mTimeDayWheelView.setStyle(wheelViewStyle);
        mTimeDayWheelView.setSelection(MyDateUtils.getCurrentDay() - 1);
        mTimeDayWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mTimeDayWheelView.setSkin(WheelView.Skin.Holo);
        mTimeDayWheelView.setWheelData(dayList);

        //小时
        List<String> hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourList.add(String.format("%02d", i));
        }
        mHourWheelView = findViewById(R.id.wheel_view_hour);
        mHourWheelView.setLoop(true);
        mHourWheelView.setStyle(wheelViewStyle);
        mHourWheelView.setSelection(MyDateUtils.getTwentyFourHour());
        mHourWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mHourWheelView.setSkin(WheelView.Skin.Holo);
        mHourWheelView.setWheelData(hourList);

        //分钟
        List<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minuteList.add(String.format("%02d", i));
        }
        mMinuteWheelView = findViewById(R.id.wheel_view_minute);
        mMinuteWheelView.setLoop(true);
        mMinuteWheelView.setStyle(wheelViewStyle);
        mMinuteWheelView.setSelection(MyDateUtils.getCurrentMinute());
        mMinuteWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMinuteWheelView.setSkin(WheelView.Skin.Holo);
        mMinuteWheelView.setWheelData(minuteList);

        mConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = mYearWheelView.getSelectionItem().toString();
                String month = mMonthWheelView.getSelectedItemPosition() == 0 ? "" : mMonthWheelView.getSelectionItem().toString();
                String day = mTimeDayWheelView.getSelectedItemPosition() == 0 ? "" : mTimeDayWheelView.getSelectionItem().toString();

                String hour = mHourWheelView.getSelectionItem().toString();
                String minute = mMinuteWheelView.getSelectionItem().toString();

                dateSelectListener.selectBirDate(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                dismiss();
            }
        });
    }

}