package com.ydys.qmb.ui.fragment;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.ui.activity.NameDetailActivity;
import com.ydys.qmb.ui.custom.CustomDateDialog;
import com.ydys.qmb.ui.custom.LoginDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ExplainFragment extends BaseFragment implements CustomDateDialog.DateSelectListener {

    @BindView(R.id.iv_home_top)
    ImageView mTopImageIv;

    @BindView(R.id.et_xs)
    EditText mXsEt;

    @BindView(R.id.et_name)
    EditText mNameEt;

    @BindView(R.id.tv_bir_date)
    TextView mBirDateTv;

    @BindView(R.id.rg_sex)
    RadioGroup mSexRg;

    @BindView(R.id.btn_birth_in)
    Button mBirthInBtn;

    @BindView(R.id.btn_birth_not)
    Button mBirthNotBtn;

    CustomDateDialog customDateDialog;

    private int selectSex = -1;

    LoginDialog loginDialog;

    @Override
    protected int getContentView() {
        return R.layout.fragment_explain;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        loginDialog = new LoginDialog(getActivity(), R.style.login_dialog);

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("explain_sur_name"))) {
            mXsEt.setText(SPUtils.getInstance().getString("explain_sur_name"));
        }

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("explain_name"))) {
            mNameEt.setText(SPUtils.getInstance().getString("explain_name"));
        }

        mBirDateTv.setText(TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));
        customDateDialog = new CustomDateDialog(getActivity(), R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);

        mBirthInBtn.setSelected(true);
        mBirthInBtn.setBackgroundResource(R.drawable.common_selected_bg);

        mSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.rb_boy) {
                    selectSex = 1;
                } else {
                    selectSex = 0;
                }
            }
        });

        if (SPUtils.getInstance().getInt("explain_query_sex", -1) > -1) {
            mSexRg.check(SPUtils.getInstance().getInt("explain_query_sex", -1) == 1 ? R.id.rb_boy : R.id.rb_girl);
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

    @OnClick(R.id.layout_birth_day)
    void birDate() {
        customDateDialog.show();
    }

    @Override
    public void selectBirDate(String selectDate) {
        mBirDateTv.setText(selectDate);
    }

    @OnClick(R.id.btn_birth_in)
    void birthIn() {
        mBirthInBtn.setSelected(true);
        mBirthInBtn.setBackgroundResource(R.drawable.common_selected_bg);

        mBirthNotBtn.setSelected(false);
        mBirthNotBtn.setBackgroundResource(R.drawable.common_normal_bg);
    }

    @OnClick(R.id.btn_birth_not)
    void birthNot() {
        mBirthInBtn.setSelected(false);
        mBirthInBtn.setBackgroundResource(R.drawable.common_normal_bg);

        mBirthNotBtn.setSelected(true);
        mBirthNotBtn.setBackgroundResource(R.drawable.common_selected_bg);
    }

    @OnClick(R.id.btn_start_give_name)
    void startGiveName() {

        if (StringUtils.isEmpty(mXsEt.getText())) {
            Toasty.normal(getActivity(), "请输入你的姓氏").show();
            return;
        }

        if (StringUtils.isEmpty(mNameEt.getText())) {
            Toasty.normal(getActivity(), "请输入你的名字").show();
            return;
        }

        if (selectSex == -1) {
            Toasty.normal(getActivity(), "请选择性别").show();
            return;
        }

        if (StringUtils.isEmpty(mBirDateTv.getText())) {
            Toasty.normal(getActivity(), "请选择出生日期").show();
        }

        if (App.mUserInfo == null) {
            if (loginDialog != null && !loginDialog.isShowing()) {
                loginDialog.show();
            }
            return;
        }

        SPUtils.getInstance().put("explain_sur_name", mXsEt.getText().toString());
        SPUtils.getInstance().put("explain_name", mNameEt.getText().toString());
        SPUtils.getInstance().put("explain_query_sex", selectSex);

        Intent intent = new Intent(getActivity(), NameDetailActivity.class);
        intent.putExtra("surname", mXsEt.getText().toString());
        intent.putExtra("name", mNameEt.getText().toString());
        intent.putExtra("query_sex", selectSex);
        intent.putExtra("query_birth_date", mBirDateTv.getText().toString());
        intent.putExtra("show_type", 2);
        startActivity(intent);
    }
}
