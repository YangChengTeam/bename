package com.ydys.qmb.util;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-M-dd H:mm", Locale.getDefault());

    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);//月
        return month;
    }

    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.DAY_OF_MONTH);//日
        return month;
    }

    //24小时制
    public static int getTwentyFourHour() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        return mHour;
    }

    //12小时制
    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);//时
        return mHour;
    }

    public static int getCurrentMinute() {
        Calendar c = Calendar.getInstance();
        int mMinute = c.get(Calendar.MINUTE);//分
        return mMinute;
    }

    public static int getCurrentSecond() {
        Calendar c = Calendar.getInstance();
        int mSecond = c.get(Calendar.SECOND);//秒
        return mSecond;
    }

    public static int getCurrentSolt() {
        int solt = 0;
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        if (mHour >= 0 && mHour < 5) {
            solt = 0;
        }
        if (mHour >= 5 && mHour < 8) {
            solt = 1;
        }
        if (mHour >= 8 && mHour < 12) {
            solt = 2;
        }
        if (mHour >= 12 && mHour < 13) {
            solt = 3;
        }
        if (mHour >= 13 && mHour < 18) {
            solt = 4;
        }
        if (mHour >= 18 && mHour < 19) {
            solt = 5;
        }
        if (mHour >= 19 && mHour < 24) {
            solt = 6;
        }
        return solt;
    }

    public static String getShengXiao(String qDate) {
        return TimeUtils.getChineseZodiac(qDate, sdf);
    }

    public static String getXingZuo(String qDate) {
        return TimeUtils.getZodiac(qDate, sdf);
    }

    public static String getNongLiDate(String qDate) {
        if (StringUtils.isEmpty(qDate)) {
            return "";
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(TimeUtils.string2Date(qDate, sdf));
        int year = ca.get(Calendar.YEAR);//年份数值
        int month = ca.get(Calendar.MONTH);//第几个月
        int day = ca.get(Calendar.DAY_OF_MONTH);
        int hour = ca.get(Calendar.HOUR_OF_DAY);

        //Logger.i("时辰" + getShiChen(hour));
        int[] temp = LunarCalendar.solarToLunar(year, month + 1, day);
        String lastDate = temp[0] + "-" + temp[1] + "-" + temp[2];
        lastDate = dataToUpper(TimeUtils.string2Date(lastDate, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))) + " "+ getShiChen(hour) + "时";
        return lastDate;
    }

    public static void test() {
        try {
            //Logger.i("当前时间--->" + sdf1.format(sdf1.parse("2019-11-12 09:12")));

            Calendar ca = Calendar.getInstance();
            ca.setTime(sdf.parse("2019-6-12 09:12"));

            int year = ca.get(Calendar.YEAR);//年份数值
            int month = ca.get(Calendar.MONTH);//第几个月
            int day = ca.get(Calendar.DAY_OF_MONTH);
            int hour = ca.get(Calendar.HOUR_OF_DAY);
            Logger.i("测试--->" + year + "---" + month + "---" + day + "---" + hour);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getDateInfo(String dateStr) {
        try {
            int[] dateInfo = new int[4];

            Calendar ca = Calendar.getInstance();
            ca.setTime(sdf.parse(dateStr));

            int year = ca.get(Calendar.YEAR);//年份数值
            int month = ca.get(Calendar.MONTH);//第几个月
            int day = ca.get(Calendar.DAY_OF_MONTH);
            int hour = ca.get(Calendar.HOUR_OF_DAY);
            dateInfo[0] = year;
            dateInfo[1] = month + 1;
            dateInfo[2] = day;
            dateInfo[3] = hour;

            return dateInfo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年  " + monthToUppder(month) + "月  " + dayToUppder(day);
    }

    /***
     * <b>function:</b> 将数字转化为大写
     * @createDate 2010-5-27 上午10:28:12
     * @param num 数字
     * @return 转换后的大写数字
     */
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        //String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String u[] = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    /***
     * <b>function:</b> 月转化为大写
     * @createDate 2010-5-27 上午10:41:42
     * @param month 月份
     * @return 返回转换后大写月份
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    /***
     * <b>function:</b> 日转化为大写
     * @createDate 2010-5-27 上午10:43:32
     * @param day 日期
     * @return 转换大写的日期格式
     */
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    public static String getShiChen(int hour) {
        String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申",
                "酉", "戌", "亥"};
        return Zhi[hourZ(hour)];
    }

    /**
     * 返回 小时对应的 支的索引
     *
     * @param hour
     * @return
     */
    public static int hourZ(int hour) {
        if (hour >= 23 || hour < 1)
            return 0;
        else if (hour >= 1 && hour < 3)
            return 1;
        else if (hour >= 3 && hour < 5)
            return 2;
        else if (hour >= 5 && hour < 7)
            return 3;
        else if (hour >= 7 && hour < 9)
            return 4;
        else if (hour >= 9 && hour < 11)
            return 5;
        else if (hour >= 11 && hour < 13)
            return 6;
        else if (hour >= 13 && hour < 15)
            return 7;
        else if (hour >= 15 && hour < 17)
            return 8;
        else if (hour >= 17 && hour < 19)
            return 9;
        else if (hour >= 19 && hour < 21)
            return 10;
        else if (hour >= 21 && hour < 23)
            return 11;
        return 0;
    }

}
