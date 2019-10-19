package com.ydys.qmb.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.ui.activity.NameListActivity;
import com.ydys.qmb.ui.custom.CustomDateDialog;
import com.ydys.qmb.ui.custom.LoginDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HomeFragment extends BaseFragment implements CustomDateDialog.DateSelectListener {

    @BindView(R.id.iv_home_top)
    ImageView mTopImageIv;

    @BindView(R.id.et_xs)
    EditText mXsEt;

    @BindView(R.id.rg_name_model)
    RadioGroup mNameModelRg;

    @BindView(R.id.rg_sex)
    RadioGroup mSexRg;

    @BindView(R.id.tv_bir_date)
    TextView mBirDateTv;

    @BindView(R.id.btn_birth_in)
    Button mBirthInBtn;

    @BindView(R.id.btn_birth_not)
    Button mBirthNotBtn;

    CustomDateDialog customDateDialog;

    private int selectNameModel;

    private int selectSex = -1;

    private boolean isBirth = true;

    LoginDialog loginDialog;

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void showReadPhone() {
        if (loginDialog != null && !loginDialog.isShowing()) {
            loginDialog.show();
        }
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneDenied() {
        Toast.makeText(getActivity(), R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForReadPhone(PermissionRequest request) {
        showRationaleDialog(R.string.permission_read_phone_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneNeverAskAgain() {
        Toast.makeText(getActivity(), R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initVars() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("birth_date"))) {
            mBirDateTv.setText(SPUtils.getInstance().getString("birth_date"));
        } else {
            mBirDateTv.setText(TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));
        }

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("sur_name"))) {
            mXsEt.setText(SPUtils.getInstance().getString("sur_name"));
            mXsEt.setSelection(SPUtils.getInstance().getString("sur_name").length());
        }
        loginDialog = new LoginDialog(getActivity(), R.style.login_dialog);

        customDateDialog = new CustomDateDialog(getActivity(), R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);

        mBirthInBtn.setSelected(true);
        mBirthInBtn.setBackgroundResource(R.drawable.common_selected_bg);

        mNameModelRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (group.getCheckedRadioButtonId() == R.id.rb_single) {
                    selectNameModel = 1;
                } else {
                    selectNameModel = 2;
                }
            }
        });

        if (SPUtils.getInstance().getInt("name_model", 0) > 0) {
            mNameModelRg.check(SPUtils.getInstance().getInt("name_model", 0) == 1 ? R.id.rb_single : R.id.rb_double);
        }

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

        if (SPUtils.getInstance().getInt("query_sex", -1) > -1) {
            mSexRg.check(SPUtils.getInstance().getInt("query_sex", -1) == 1 ? R.id.rb_boy : R.id.rb_girl);
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

    @OnClick({R.id.layout_birth_day})
    void birDate() {
        customDateDialog.show();
    }

    @Override
    public void selectBirDate(String selectDate) {
        mBirDateTv.setText(selectDate);
    }

    @OnClick(R.id.btn_birth_in)
    void birthIn() {
        isBirth = true;
        mBirthInBtn.setSelected(true);
        mBirthInBtn.setBackgroundResource(R.drawable.common_selected_bg);

        mBirthNotBtn.setSelected(false);
        mBirthNotBtn.setBackgroundResource(R.drawable.common_normal_bg);
    }

    @OnClick(R.id.btn_birth_not)
    void birthNot() {
        isBirth = false;
        mBirthInBtn.setSelected(false);
        mBirthInBtn.setBackgroundResource(R.drawable.common_normal_bg);

        mBirthNotBtn.setSelected(true);
        mBirthNotBtn.setBackgroundResource(R.drawable.common_selected_bg);
    }

    @OnClick(R.id.btn_start_give_name)
    void startGiveName() {

        if (StringUtils.isEmpty(mXsEt.getText())) {
            Toasty.normal(getActivity(), "请输入姓氏").show();
            return;
        }

        if (selectNameModel == 0) {
            Toasty.normal(getActivity(), "请选择取名模式").show();
            return;
        }

        if (selectSex == -1) {
            Toasty.normal(getActivity(), "请选择性别").show();
            return;
        }

        if (App.mUserInfo == null) {
            HomeFragmentPermissionsDispatcher.showReadPhoneWithPermissionCheck(this);
            return;
        }

        toNameList();
    }

    public void toNameList(){
        SPUtils.getInstance().put("sur_name", mXsEt.getText().toString());
        SPUtils.getInstance().put("name_model", selectNameModel);
        SPUtils.getInstance().put("query_sex", selectSex);

        SPUtils.getInstance().put("birth_date", mBirDateTv.getText().toString());

        Intent intent = new Intent(getActivity(), NameListActivity.class);
        intent.putExtra("sur_name", mXsEt.getText().toString());
        intent.putExtra("name_model", selectNameModel);
        intent.putExtra("query_sex", selectSex);
        intent.putExtra("is_birth", isBirth);//默认已出生
        intent.putExtra("birth_date", mBirDateTv.getText());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("login_success")) {
            toNameList();
        }
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
