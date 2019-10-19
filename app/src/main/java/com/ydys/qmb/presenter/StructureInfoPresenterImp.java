package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.base.BasePresenterImp;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.StructureInfoRet;
import com.ydys.qmb.bean.VersionInfoRet;
import com.ydys.qmb.model.StructureInfoModelImp;
import com.ydys.qmb.model.VersionInfoModelImp;
import com.ydys.qmb.view.StructureInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class StructureInfoPresenterImp extends BasePresenterImp<StructureInfoView, StructureInfoRet> implements StructureInfoPresenter {

    private Context context = null;
    private StructureInfoModelImp structureInfoModelImp = null;

    public StructureInfoPresenterImp(StructureInfoView view, Context context) {
        super(view);
        this.context = context;
        this.structureInfoModelImp = new StructureInfoModelImp(context);
    }

    @Override
    public void getStructureInfo(String nameId, String xing, String ming) {
        structureInfoModelImp.getStructureInfo(nameId, xing, ming, this);
    }

}
