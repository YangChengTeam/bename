package com.ydys.qmb.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ydys.qmb.R;

public class ConfigDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private LinearLayout mConfigLayout;

    private LinearLayout mCancelLayout;

    private String title;

    private String content;

    private ConfigListener configListener;

    private String cancelTxt;

    private String configTxt;

    private int topImageRes;

    public interface ConfigListener {
        void config();

        void cancel();
    }

    public void setConfigListener(ConfigListener configListener) {
        this.configListener = configListener;
    }

    public ConfigDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ConfigDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public ConfigDialog(Context context, int themeResId, int imgRes, String titleStr, String contentStr) {
        super(context, themeResId);
        this.mContext = context;
        this.topImageRes = imgRes;
        this.title = titleStr;
        this.content = contentStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);
        mCancelLayout = findViewById(R.id.layout_cancel);

        mConfigLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_config:
                configListener.config();
                this.dismiss();
                break;
            case R.id.layout_cancel:
                configListener.cancel();
                this.dismiss();
                break;
            default:
                break;
        }
    }
}