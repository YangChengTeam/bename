package com.ydys.qmb.presenter;

import android.content.Context;

import com.ydys.qmb.model.executer.JobExecutor;
import com.ydys.qmb.model.executer.RxJavaExecuter;
import com.ydys.qmb.model.executer.UIThread;


/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class Presenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public Presenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
