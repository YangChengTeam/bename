package com.ydys.qmb.bean;

public class AddKeepInfo {
    private String name_id;//当前姓名的ID
    private String user_id;//用户ID
    private Long uptime;//更新收藏信息的时间
    private int status;//名字收藏状态(0:未收藏 ，1：已收藏)

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
