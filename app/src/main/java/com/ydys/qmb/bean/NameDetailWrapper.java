package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NameDetailWrapper {

    private String name_id;//当前名字的ID
    private String sur_name;//完整姓名
    private double wuge_score;//综合五格分数
    private int is_collect;//是否收藏(0:未收藏 1：已收藏)

    @SerializedName("word_info")
    private List<WordInfo> wordInfo;

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public double getWuge_score() {
        return wuge_score;
    }

    public void setWuge_score(double wuge_score) {
        this.wuge_score = wuge_score;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<WordInfo> getWordInfo() {
        return wordInfo;
    }

    public void setWordInfo(List<WordInfo> wordInfo) {
        this.wordInfo = wordInfo;
    }
}
