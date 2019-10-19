package com.ydys.qmb.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.ydys.qmb.bean.FunInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.util.FunImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cl on 2018/5/3.
 */

public class DataService extends IntentService {
    public DataService() {
        super("");
    }

    public static void startService(Context context, List<FunInfo> datas, String subtype) {
        Intent intent = new Intent(context, DataService.class);
        //intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) datas);
        bundle.putString("subtype", subtype);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<FunInfo> datas = (List<FunInfo>) intent.getSerializableExtra("data");

        String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(datas, subtype);
    }

    private void handleGirlItemData(List<FunInfo> datas, String subtype) {
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        for (FunInfo data : datas) {
            Bitmap bitmap = FunImageLoader.load(this, Constants.LOCAL_GIF_URL + data.getCoverImage());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }

            data.setSubtype(subtype);
        }
        EventBus.getDefault().post(datas);
    }
}