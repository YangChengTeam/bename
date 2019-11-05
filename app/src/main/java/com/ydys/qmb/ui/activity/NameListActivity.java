package com.ydys.qmb.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AddKeepInfoRet;
import com.ydys.qmb.bean.BuyVipRet;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.NameInfoRet;
import com.ydys.qmb.bean.PayInfoRet;
import com.ydys.qmb.bean.PayRequestParams;
import com.ydys.qmb.bean.PayResult;
import com.ydys.qmb.bean.PriceInfoRet;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.bean.WxPayInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.AddKeepInfoPresenterImp;
import com.ydys.qmb.presenter.BuyVipInfoPresenterImp;
import com.ydys.qmb.presenter.NameInfoPresenterImp;
import com.ydys.qmb.presenter.PayInfoPresenterImp;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.presenter.VipInfoPresenterImp;
import com.ydys.qmb.ui.adapter.NameAdapter;
import com.ydys.qmb.ui.custom.LoginDialog;
import com.ydys.qmb.ui.custom.NormalDecoration;
import com.ydys.qmb.ui.custom.PayDialog;
import com.ydys.qmb.util.MyDateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/20.
 */

public class NameListActivity extends BaseActivity implements IBaseView, PayDialog.PayWayListener {

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.name_list)
    RecyclerView nameListView;

    @BindView(R.id.layout_pay_bottom)
    LinearLayout mPayBottomLayout;

    @BindView(R.id.layout_loading)
    LinearLayout mLoadingDataLayout;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    private TextView mXsTv;

    private TextView mSexTv;

    private TextView mShengXiaoTv;

    private TextView mXingZuoTv;

    private TextView mGongliDateTv;

    private TextView mNongliDateTv;

    private ImageView mEditNameIv;

    NameAdapter nameAdapter;

    PayDialog payDialog;

    @BindView(R.id.btn_pay)
    Button mPayBtn;

    LoginDialog loginDialog;

    NameInfoPresenterImp nameInfoPresenterImp;

    PayInfoPresenterImp payInfoPresenterImp;

    BuyVipInfoPresenterImp buyVipInfoPresenterImp;

    AddKeepInfoPresenterImp addKeepInfoPresenterImp;

    private VipInfoPresenterImp vipInfoPresenterImp;

    private String querySurName;

    private int queryNameModel;

    private int querySex;

    private boolean queryIsBirth;

    private String queryBirthDate;

    private IWXAPI api;

    private UserInfo mUserInfo;

    private String payWay = "wxpay";

    private int currentPage = 1;

    private int pageSize = 20;

    private int currentPos = -1;

    QueryNameInfo queryNameInfo;

    private ProgressDialog progressDialog = null;

    private boolean isToPay;

    private double lastNameScore;

    private int lastClickIndex = -1;

    //全局定义
    private long lastClickTime = 0L;

    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 500;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_list;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                        Toasty.normal(NameListActivity.this, "支付成功").show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                        Toasty.normal(NameListActivity.this, "支付失败").show();
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void initVars() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            querySurName = bundle.getString("sur_name");
            queryNameModel = bundle.getInt("name_model");
            querySex = bundle.getInt("query_sex");
            queryIsBirth = bundle.getBoolean("is_birth", true);
            queryBirthDate = bundle.getString("birth_date");
        }

        nameInfoPresenterImp = new NameInfoPresenterImp(this, this);
        payInfoPresenterImp = new PayInfoPresenterImp(this, this);
        buyVipInfoPresenterImp = new BuyVipInfoPresenterImp(this, this);
        addKeepInfoPresenterImp = new AddKeepInfoPresenterImp(this, this);

        loginDialog = new LoginDialog(this, R.style.login_dialog);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("支付结果同步中···");

        View topView = LayoutInflater.from(this).inflate(R.layout.top_base_name_view, null);
        mEditNameIv = topView.findViewById(R.id.iv_edit_name);
        mXsTv = topView.findViewById(R.id.tv_surname);
        mSexTv = topView.findViewById(R.id.tv_sex);
        mShengXiaoTv = topView.findViewById(R.id.tv_sx);
        mXingZuoTv = topView.findViewById(R.id.tv_star);
        mGongliDateTv = topView.findViewById(R.id.tv_gongli_date);
        mNongliDateTv = topView.findViewById(R.id.tv_nongli_date);
        mEditNameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Logger.i("nongli--->" + MyDateUtils.getNongLiDate(queryBirthDate));

        mXsTv.setText(querySurName);
        mSexTv.setText(querySex == 1 ? "男" : "女");
        mShengXiaoTv.setText(MyDateUtils.getShengXiao(queryBirthDate));
        mXingZuoTv.setText(MyDateUtils.getXingZuo(queryBirthDate));
        mGongliDateTv.setText(queryBirthDate);
        mNongliDateTv.setText(MyDateUtils.getNongLiDate(queryBirthDate));

        nameAdapter = new NameAdapter(this, null);
        nameListView.setLayoutManager(new LinearLayoutManager(this));
        nameListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.transparent), SizeUtils.dp2px(12)));
        nameListView.setAdapter(nameAdapter);
        nameAdapter.addHeaderView(topView);


        //发布
//        RxView.clicks(mAddNoteBtn).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
//            @Override
//            public void call(Void aVoid) {
//            }
//        });


        nameListView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME) {
                    return;
                }
                lastClickTime = System.currentTimeMillis();

                Logger.i("name list addOnItemTouchListener--->");
                lastClickIndex = position;

                Intent intent = new Intent(NameListActivity.this, NameDetailActivity.class);
                intent.putExtra("surname", querySurName);
                String name = StringUtils.isEmpty(nameAdapter.getData().get(position).getSurName()) ? "" : nameAdapter.getData().get(position).getSurName().substring(1);
                intent.putExtra("name", name);
                intent.putExtra("name_id", nameAdapter.getData().get(position).getId() + "");
                intent.putExtra("query_birth_date", queryBirthDate);
                intent.putExtra("query_sex", querySex);
                intent.putExtra("show_type", 1);
                intent.putExtra("last_click_index", lastClickIndex);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.i("child id" + view.getId());

                if (view.getId() == R.id.layout_blur) {
                    payButton();
                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        nameAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (nameAdapter.isBuyVip()) {
                    currentPage++;
                    queryNameInfo.setPage(currentPage);
                    if (currentPage > 1) {
                        queryNameInfo.setLastNameScore(lastNameScore);
                    }
                    nameInfoPresenterImp.getNameList(queryNameInfo);
                } else {
                    nameAdapter.loadMoreEnd(true);
                }
            }
        }, nameListView);

        nameAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_add_keep) {
                    currentPos = position;

                    QueryNameInfo addParams = new QueryNameInfo();
                    addParams.setUser_id(mUserInfo.getId());
                    addParams.setType(mUserInfo.getLogin_type());
                    addParams.setPhone(mUserInfo.getPhone());
                    addParams.setOpenid(mUserInfo.getOpenid());
                    addParams.setNameId(nameAdapter.getData().get(position).getId());

                    addKeepInfoPresenterImp.addKeep(addParams);
                }
            }
        });
        payDialog = new PayDialog(this, R.style.login_dialog);
        payDialog.setPayWayListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, "wx096649d1b91ee026");
        if (App.mUserInfo != null) {
            mUserInfo = App.mUserInfo;
        }

        vipInfoPresenterImp = new VipInfoPresenterImp(this, this);
        vipInfoPresenterImp.vipPrice();

        queryNameInfo = new QueryNameInfo();
        queryNameInfo.setUser_id(mUserInfo.getId());
        queryNameInfo.setType(mUserInfo.getLogin_type());
        queryNameInfo.setPhone(mUserInfo.getPhone());
        queryNameInfo.setOpenid(mUserInfo.getOpenid());
        queryNameInfo.setPage(currentPage);
        queryNameInfo.setXing(querySurName);
        queryNameInfo.setSingle(queryNameModel);
        queryNameInfo.setSex(querySex);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nameInfoPresenterImp.getNameList(queryNameInfo);
            }
        }, 1500);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (payDialog != null && payDialog.isShowing()) {
            payDialog.dismiss();
        }

        if (isToPay) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //查询这个姓氏是否购买
                    buyVipInfoPresenterImp.isBuyVip(mUserInfo != null ? mUserInfo.getId() : "", querySurName);
                }
            }, 3000);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("last_click_index")) {
            int tempLastClick = messageEvent.getLastClickIndex();
            int isKeep = messageEvent.getIsKeep();
            if (nameAdapter != null && nameAdapter.getData() != null && tempLastClick < nameAdapter.getData().size()) {
                nameAdapter.getData().get(tempLastClick).setIsCollect(isKeep);
                nameAdapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.btn_pay)
    void payButton() {
        if (payDialog != null && !payDialog.isShowing()) {
            Map<String, Object> clickMap = new HashMap<String, Object>();
            clickMap.put("pay_click", "click_num");

            MobclickAgent.onEventObject(this, "open_pay_dialog", clickMap);
            //MobclickAgent.onEvent(this,"open_pay_dialog");
            payDialog.show();
            if (App.priceInfo != null) {
                payDialog.setPayInfo(querySurName, querySex, queryBirthDate, App.priceInfo.getmPrice(), App.priceInfo.getPrice(), querySurName);
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object tData) {

        if (tData != null) {
            if (tData instanceof NameInfoRet) {
                //隐藏加载界面
                mLoadingDataLayout.setVisibility(View.GONE);

                if (((NameInfoRet) tData).getCode() == Constants.SUCCESS) {
                    if (currentPage == 1) {
                        if (((NameInfoRet) tData).getData().size() > 0) {
                            mNoDataLayout.setVisibility(View.GONE);
                            nameListView.setVisibility(View.VISIBLE);
                            nameAdapter.setNewData(((NameInfoRet) tData).getData());

                            //查询这个姓氏是否购买
                            buyVipInfoPresenterImp.isBuyVip(mUserInfo != null ? mUserInfo.getId() : "", querySurName);
                        } else {
                            mNoDataLayout.setVisibility(View.VISIBLE);
                            nameListView.setVisibility(View.GONE);
                        }

                        nameAdapter.loadMoreComplete();
                        lastNameScore = nameAdapter.getData().get(nameAdapter.getData().size() - 1).getWugeScore();
                    } else {
                        nameAdapter.addData(((NameInfoRet) tData).getData());

                        if (((NameInfoRet) tData).getData().size() == pageSize) {
                            nameAdapter.loadMoreComplete();
                            lastNameScore = nameAdapter.getData().get(nameAdapter.getData().size() - 1).getWugeScore();
                        } else {
                            nameAdapter.loadMoreEnd(true);
                        }
                    }
                } else {
                    if (currentPage == 1) {
                        mNoDataLayout.setVisibility(View.VISIBLE);
                        nameListView.setVisibility(View.GONE);
                    }

                    nameAdapter.loadMoreEnd(true);
                }
            }
            if (tData instanceof PayInfoRet) {
                if (((PayInfoRet) tData).getData() != null && ((PayInfoRet) tData).getCode() == Constants.SUCCESS) {
                    if (payWay.equals("wxpay")) {
                        WxPayInfo wxPayInfo = ((PayInfoRet) tData).getData().getWx_orderinfo();
                        PayReq req = new PayReq();
                        req.appId = wxPayInfo.getParams().getAppid();
                        req.partnerId = wxPayInfo.getParams().getMch_id();
                        req.prepayId = wxPayInfo.getParams().getPrepayid();
                        req.nonceStr = wxPayInfo.getParams().getNoncestr();
                        req.timeStamp = wxPayInfo.getParams().getTimestamp();
                        req.packageValue = wxPayInfo.getParams().getPayPackage();
                        req.sign = wxPayInfo.getParams().getSign();
                        api.sendReq(req);
                        Logger.i("send wx pay --->");
                    } else {

                        final String orderInfo = ((PayInfoRet) tData).getData().getAli_orderinfo();   // 订单信息
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(NameListActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                        Logger.i("send alipay pay --->");
                    }
                }
            }

            if (tData instanceof BuyVipRet) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (((BuyVipRet) tData).getCode() == Constants.SUCCESS) {
                    Logger.i("购买VIP信息--->" + JSON.toJSONString(((BuyVipRet) tData).getData()));
                    nameAdapter.setBuyVip(true);
                    nameAdapter.notifyDataSetChanged();
                    mPayBottomLayout.setVisibility(View.GONE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.setMargins(SizeUtils.dp2px(12), 1, SizeUtils.dp2px(12), 0);
                    nameListView.setLayoutParams(params);

                    if (isToPay) {
                        isToPay = false;

                        currentPage = 1;
                        queryNameInfo.setPage(currentPage);
                        nameInfoPresenterImp.getNameList(queryNameInfo);
                    }

                } else {
                    mPayBottomLayout.setVisibility(View.VISIBLE);
                    Logger.i("未查询到订单信息--->");
                }
            }

            if (tData instanceof AddKeepInfoRet) {
                if (((AddKeepInfoRet) tData).getCode() == Constants.SUCCESS) {
                    nameAdapter.getData().get(currentPos).setIsCollect(((AddKeepInfoRet) tData).getData().getStatus());
                    nameAdapter.notifyDataSetChanged();
                }
            }

            //获取产品价格
            if (tData instanceof PriceInfoRet) {
                if (tData != null && ((PriceInfoRet) tData).getCode() == Constants.SUCCESS) {
                    App.priceInfo = ((PriceInfoRet) tData).getData();
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        nameAdapter.loadMoreEnd(true);
        mLoadingDataLayout.setVisibility(View.GONE);
        if (currentPage == 1) {
            mNoDataLayout.setVisibility(View.VISIBLE);
            nameListView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void wxpay() {
        if (mUserInfo != null) {
            isToPay = true;
            payWay = "wxpay";
            PayRequestParams payRequestParams = new PayRequestParams();
            payRequestParams.setUser_id(mUserInfo.getId());
            payRequestParams.setType(mUserInfo.getLogin_type());
            payRequestParams.setPhone(mUserInfo.getPhone());
            payRequestParams.setOpenid(mUserInfo.getOpenid());
            payRequestParams.setPay_way(payWay);
            payRequestParams.setAmount(App.priceInfo != null ? App.priceInfo.getmPrice() + "" : "0");
            payRequestParams.setXing(querySurName);
            payRequestParams.setTitle("解锁[" + querySurName + "]姓所有高分吉祥美名");
            payInfoPresenterImp.createOrder(payRequestParams);
        }

    }

    @Override
    public void alipay() {
        if (mUserInfo != null) {
            isToPay = true;
            payWay = "alipay";
            PayRequestParams payRequestParams = new PayRequestParams();
            payRequestParams.setUser_id(mUserInfo.getId());
            payRequestParams.setType(mUserInfo.getLogin_type());
            payRequestParams.setPhone(mUserInfo.getPhone());
            payRequestParams.setOpenid(mUserInfo.getOpenid());
            payRequestParams.setPay_way(payWay);
            payRequestParams.setAmount(App.priceInfo != null ? App.priceInfo.getmPrice() + "" : "0");
            payRequestParams.setXing(querySurName);
            payRequestParams.setTitle("解锁[" + querySurName + "]姓所有高分吉祥美名");
            payInfoPresenterImp.createOrder(payRequestParams);
        }
    }

    @Override
    public void payClose() {
        Map<String, Object> clickMap = new HashMap<String, Object>();
        clickMap.put("pay_close", "close_num");
        MobclickAgent.onEventObject(this, "close_pay_dialog", clickMap);
        //MobclickAgent.onEvent(this,"close_pay_dialog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
