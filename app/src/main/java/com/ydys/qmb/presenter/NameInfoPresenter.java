package com.ydys.qmb.presenter;

import com.ydys.qmb.bean.QueryNameInfo;

/**
 * Created by admin on 2017/4/7.
 */

public interface NameInfoPresenter {
    void getNameList(QueryNameInfo queryNameInfo);

    void getCollectionList(QueryNameInfo queryNameInfo);
}
