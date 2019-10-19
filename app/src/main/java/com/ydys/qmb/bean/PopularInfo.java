package com.ydys.qmb.bean;

public class PopularInfo {
    private Integer[] male;
    private Integer[] female;
    private Integer[] second_male;
    private Integer[] second_female;

    private String male_per;//男性比例
    private String female_per;//女性比例
    private String message;//男女比例分析
    private String good_message;//好听程度分析
    private String good_per;//好听程度比例
    private String fashion_message;//时尚程度分析
    private String fashion_per;//时尚程度比例
    private String special_message;//独特程度分析
    private String special_per;//独特程度比例

    public Integer[] getMale() {
        return male;
    }

    public void setMale(Integer[] male) {
        this.male = male;
    }

    public Integer[] getFemale() {
        return female;
    }

    public void setFemale(Integer[] female) {
        this.female = female;
    }

    public Integer[] getSecond_male() {
        return second_male;
    }

    public void setSecond_male(Integer[] second_male) {
        this.second_male = second_male;
    }

    public Integer[] getSecond_female() {
        return second_female;
    }

    public void setSecond_female(Integer[] second_female) {
        this.second_female = second_female;
    }

    public String getMale_per() {
        return male_per;
    }

    public void setMale_per(String male_per) {
        this.male_per = male_per;
    }

    public String getFemale_per() {
        return female_per;
    }

    public void setFemale_per(String female_per) {
        this.female_per = female_per;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGood_message() {
        return good_message;
    }

    public void setGood_message(String good_message) {
        this.good_message = good_message;
    }

    public String getGood_per() {
        return good_per;
    }

    public void setGood_per(String good_per) {
        this.good_per = good_per;
    }

    public String getFashion_message() {
        return fashion_message;
    }

    public void setFashion_message(String fashion_message) {
        this.fashion_message = fashion_message;
    }

    public String getFashion_per() {
        return fashion_per;
    }

    public void setFashion_per(String fashion_per) {
        this.fashion_per = fashion_per;
    }

    public String getSpecial_message() {
        return special_message;
    }

    public void setSpecial_message(String special_message) {
        this.special_message = special_message;
    }

    public String getSpecial_per() {
        return special_per;
    }

    public void setSpecial_per(String special_per) {
        this.special_per = special_per;
    }
}
