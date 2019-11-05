package com.ydys.qmb.model;

import com.ydys.qmb.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface AgentInfoModel<T> {
    void addAgent(String imei, String agentId,String siteId, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
