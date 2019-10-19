package com.ydys.qmb.bean;

public class AgentInfoRet extends ResultInfo {
    private String imei;//手机串号
    private String agent;//来源渠道
    private String ip;//用户IP

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
