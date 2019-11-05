package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AgentInfoRet;
import com.ydys.qmb.bean.ResultInfo;
import com.ydys.qmb.model.AgentInfoModelImp;

/**
 * Created by admin on 2017/4/7.
 */

public class AgentInfoPresenterImp extends BasePresenterImp<IBaseView, AgentInfoRet> implements AgentInfoPresenter {

    private Context context = null;
    private AgentInfoModelImp agentInfoModelImp = null;

    public AgentInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.agentInfoModelImp = new AgentInfoModelImp(context);
    }

    @Override
    public void addAgent(String imei, String agentId, String siteId) {
        agentInfoModelImp.addAgent(imei, agentId, siteId, this);
    }
}
