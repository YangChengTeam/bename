package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangdinghui on 2019/4/23.
 */
public class HotWordInfo {
    private String id;
    @SerializedName("hot_word")
    private String hotWord;
    @SerializedName("search_count")
    private int searchCount;
    @SerializedName("word_type")
    private int wordType;
    @SerializedName("is_show")
    private int isShow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public int getWordType() {
        return wordType;
    }

    public void setWordType(int wordType) {
        this.wordType = wordType;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}
