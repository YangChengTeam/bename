package com.ydys.qmb.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydys.qmb.R;


public class VersionUpdateDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    TextView mVersionCodeTv;

    TextView mContentTv;

    Button mUpdateVersionBtn;

    ImageView mCloseImageView;

    private String versionCode;

    private String versionContent;

    private int isForceUpdate;//0:不强制，1：强制

    UpdataAPPProgressBar updataAPPProgressBar;

    TextView mProgressBgTv;

    public interface UpdateListener {
        void update();

        void updateCancel();
    }

    public UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public VersionUpdateDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public VersionUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_update_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        updataAPPProgressBar = findViewById(R.id.down_progress);
        mProgressBgTv = findViewById(R.id.tv_progress_bg);

        mVersionCodeTv = findViewById(R.id.tv_version_code);
        mContentTv = findViewById(R.id.tv_content);
        mUpdateVersionBtn = findViewById(R.id.btn_update_version);
        mCloseImageView = findViewById(R.id.iv_close);

        mVersionCodeTv.setText(versionCode);
        mContentTv.setText(versionContent);

        mUpdateVersionBtn.setOnClickListener(this);
        mCloseImageView.setOnClickListener(this);
        setCanceledOnTouchOutside(isForceUpdate == 1 ? false : true);
    }

    public void setProgress(int progress) {
        updataAPPProgressBar.setProgress(progress);
    }

    public void downFinish() {
        mUpdateVersionBtn.setVisibility(View.VISIBLE);
        updataAPPProgressBar.setVisibility(View.GONE);
        mProgressBgTv.setVisibility(View.GONE);

        mUpdateVersionBtn.setText("立即安装");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_version:
                updateListener.update();

                mUpdateVersionBtn.setVisibility(View.GONE);
                updataAPPProgressBar.setVisibility(View.VISIBLE);
                mProgressBgTv.setVisibility(View.VISIBLE);
                //this.dismiss();
                break;
            case R.id.iv_close:
                if (isForceUpdate == 0) {
                    updateListener.updateCancel();
                    this.dismiss();
                }

                break;
        }
    }

    @Override
    public void setOnKeyListener(OnKeyListener onKeyListener) {
        super.setOnKeyListener(onKeyListener);
    }
}