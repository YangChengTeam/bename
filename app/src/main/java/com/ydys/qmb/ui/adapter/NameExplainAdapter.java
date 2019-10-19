package com.ydys.qmb.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.WordInfo;

import java.util.List;

/**
 * 姓名单个字解释
 */
public class NameExplainAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {

    private Context mContext;

    public NameExplainAdapter(Context context, List<WordInfo> datas) {
        super(R.layout.name_explain_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WordInfo wordInfo) {
        try {
            String temp = wordInfo.getXing_ming_xue();

            if (StringUtils.isEmpty(temp)) {
                if (wordInfo.getJibenjieshi().indexOf("1、") > -1) {
                    temp = wordInfo.getJibenjieshi().substring(wordInfo.getJibenjieshi().indexOf("1、") + 2, wordInfo.getJibenjieshi().indexOf("2、"));
                } else if (wordInfo.getJibenjieshi().indexOf("1.") > -1) {
                    if (wordInfo.getJibenjieshi().indexOf("1.") > -1) {
                        temp = wordInfo.getJibenjieshi().substring(wordInfo.getJibenjieshi().indexOf("1.") + 2, wordInfo.getJibenjieshi().indexOf("2.") > -1 ? wordInfo.getJibenjieshi().indexOf("2.") : wordInfo.getJibenjieshi().length());
                    } else {
                        temp = wordInfo.getJibenjieshi();
                    }
                } else if (wordInfo.getJibenjieshi().indexOf("(一)") > -1) {
                    if (wordInfo.getJibenjieshi().indexOf("(一)") > -1) {
                        temp = wordInfo.getJibenjieshi().substring(wordInfo.getJibenjieshi().indexOf("(一)") + 2, wordInfo.getJibenjieshi().indexOf("(二)") > -1 ? wordInfo.getJibenjieshi().indexOf("(二)") : wordInfo.getJibenjieshi().length());
                    } else {
                        temp = wordInfo.getJibenjieshi();
                    }
                } else {
                    if (wordInfo.getJibenjieshi().indexOf("1") > -1) {
                        temp = wordInfo.getJibenjieshi().substring(wordInfo.getJibenjieshi().indexOf("1") + 1, wordInfo.getJibenjieshi().indexOf("2") > -1 ? wordInfo.getJibenjieshi().indexOf("2") : wordInfo.getJibenjieshi().length());
                    } else {
                        temp = wordInfo.getJibenjieshi();
                    }
                }
            }

            holder.setText(R.id.tv_word_name, wordInfo.getWord_name())
                    .setText(R.id.tv_word_mean, Html.fromHtml(temp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}