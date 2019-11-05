package com.ydys.qmb.bean;

public class AgentInfoWrapper {
    private String imei;//手机串号
    private int agent;//来源渠道
    private String ip;//用户IP

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
