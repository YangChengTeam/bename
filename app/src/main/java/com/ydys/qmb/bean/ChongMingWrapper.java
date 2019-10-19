package com.ydys.qmb.bean;

import java.util.List;

public class ChongMingWrapper {
    private List<GenderInfo> gender_info;

    private List<AgeInfo> age_info;

    private List<XingZuoInfo> xingzuo_info;

    private List<ShengXiaoInfo> shengxiao_info;

    private List<ProvinceInfo> province_info;

    public List<GenderInfo> getGender_info() {
        return gender_info;
    }

    public void setGender_info(List<GenderInfo> gender_info) {
        this.gender_info = gender_info;
    }

    public List<AgeInfo> getAge_info() {
        return age_info;
    }

    public void setAge_info(List<AgeInfo> age_info) {
        this.age_info = age_info;
    }

    public List<XingZuoInfo> getXingzuo_info() {
        return xingzuo_info;
    }

    public void setXingzuo_info(List<XingZuoInfo> xingzuo_info) {
        this.xingzuo_info = xingzuo_info;
    }

    public List<ShengXiaoInfo> getShengxiao_info() {
        return shengxiao_info;
    }

    public void setShengxiao_info(List<ShengXiaoInfo> shengxiao_info) {
        this.shengxiao_info = shengxiao_info;
    }

    public List<ProvinceInfo> getProvince_info() {
        return province_info;
    }

    public void setProvince_info(List<ProvinceInfo> province_info) {
        this.province_info = province_info;
    }
}
