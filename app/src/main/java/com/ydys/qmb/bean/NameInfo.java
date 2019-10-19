package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NameInfo {

    @SerializedName("name_id")
    private String id;//姓名ID
    @SerializedName("sur_name")
    private String surName;//完整姓名

    @SerializedName("wuge_score")
    private double wugeScore;//综合五格分数

    @SerializedName("is_collect")
    private int isCollect;//是否收藏(0:未收藏 1：已收藏)

    @SerializedName("word_info")
    private List<WordInfo> wordList;

    private String name;
    private int score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public double getWugeScore() {
        return wugeScore;
    }

    public void setWugeScore(double wugeScore) {

        this.wugeScore = wugeScore;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public List<WordInfo> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordInfo> wordList) {
        this.wordList = wordList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
