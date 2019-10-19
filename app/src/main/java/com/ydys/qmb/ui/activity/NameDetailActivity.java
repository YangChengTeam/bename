package com.ydys.qmb.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ydys.qmb.App;
import com.ydys.qmb.R;
import com.ydys.qmb.base.IBaseView;
import com.ydys.qmb.bean.AddKeepInfoRet;
import com.ydys.qmb.bean.MessageEvent;
import com.ydys.qmb.bean.NameDetailWrapper;
import com.ydys.qmb.bean.QueryNameInfo;
import com.ydys.qmb.bean.UserInfo;
import com.ydys.qmb.bean.WordInfo;
import com.ydys.qmb.common.Constants;
import com.ydys.qmb.presenter.AddKeepInfoPresenterImp;
import com.ydys.qmb.presenter.Presenter;
import com.ydys.qmb.ui.adapter.BaseNameWordAdapter;
import com.ydys.qmb.ui.adapter.MyFragmentAdapter;
import com.ydys.qmb.ui.fragment.BaseNameFragment;
import com.ydys.qmb.ui.fragment.DuplicateFragment;
import com.ydys.qmb.ui.fragment.PoetryFragment;
import com.ydys.qmb.ui.fragment.PopularFragment;
import com.ydys.qmb.ui.fragment.ScwgFragment;
import com.ydys.qmb.ui.fragment.StructureFragment;
import com.ydys.qmb.ui.fragment.WxbzFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class NameDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener, AppBarLayout.OnOffsetChangedListener, View.OnClickListener, IBaseView {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.tab_layout)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.name_word_list)
    RecyclerView mNameWordListView;

    @BindView(R.id.iv_keep)
    ImageView mKeepIv;

    @BindView(R.id.tv_keep)
    TextView mKeepTv;

    @BindView(R.id.tv_name_score)
    TextView mNameScoreTv;

    @BindView(R.id.layout_sliding)
    LinearLayout mSlidingLayout;

    private BaseNameWordAdapter baseNameWordAdapter;

    private final String[] TITLES = new String[]{"姓名详解", "诗词典故", "五行八字", "三才五格", "流行趋势", "字形结构"};

    private final String[] TITLES_EXPLAIN = new String[]{"姓名详解", "五行八字", "三才五格", "字形结构"};

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private MyFragmentAdapter adapter;

    public static String queryNameId;

    public static String querySurName;

    public static String queryName;

    public static String queryBirthDate;

    public static int querySex;

    private int type = 1;

    private List<WordInfo> wordList;

    BottomSheetDialog bottomSheetDialog;

    private ShareAction shareAction;

    private ProgressDialog progressDialog = null;

    AddKeepInfoPresenterImp addKeepInfoPresenterImp;

    private UserInfo mUserInfo;

    DecimalFormat df = new DecimalFormat("#0.00");

    private int lastClickIndex;

    public static String getQueryName() {
        return queryName;
    }

    public static String getQuerySurName() {
        return querySurName;
    }

    public static String getQueryNameId() {
        return queryNameId;
    }

    public static void setQueryNameId(String queryNameId) {
        NameDetailActivity.queryNameId = queryNameId;
    }

    public static String getQueryBirthDate() {
        return queryBirthDate;
    }

    public static int getQuerySex() {
        return querySex;
    }

    public List<WordInfo> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordInfo> wordList) {
        this.wordList = wordList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_detail;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
        dismissShareView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    protected void initVars() {
        EventBus.getDefault().register(this);

        if (App.mUserInfo != null) {
            mUserInfo = App.mUserInfo;
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            querySurName = bundle.getString("surname", "");
            queryName = bundle.getString("name", "");
            queryNameId = bundle.getString("name_id", "");
            queryBirthDate = bundle.getString("query_birth_date", "");
            querySex = bundle.getInt("query_sex", 0);
            type = bundle.getInt("show_type", 1);
            lastClickIndex = bundle.getInt("last_click_index", -1);
        }

        mFragmentList.add(new BaseNameFragment());
        if (type == 1) {
            mFragmentList.add(new PoetryFragment());
        }

        mFragmentList.add(new WxbzFragment());
        mFragmentList.add(new ScwgFragment());

        if (type == 1) {
            mFragmentList.add(new PopularFragment());
            //mFragmentList.add(new DuplicateFragment());
        }
        mFragmentList.add(new StructureFragment());

        addKeepInfoPresenterImp = new AddKeepInfoPresenterImp(this, this);
    }

    @Override
    protected void initViews() {
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager, type == 1 ? TITLES : TITLES_EXPLAIN);
        initDialog();
    }

    public void initDialog() {
        if (shareAction == null) {
            if (shareAction == null) {
                shareAction = new ShareAction(this);
                shareAction.setCallback(shareListener);//回调监听器

                UMWeb web = new UMWeb("http://qm.bshu.com/static/index.html");
                web.setTitle("好名陪伴一生，为您的宝宝起一个好听的名字！");//标题
                web.setThumb(new UMImage(this, R.drawable.app_share));  //缩略图
                web.setDescription("科学智能宝宝起名平台，大数据为您推荐满分好名，起到您满意为止。");//描述
                shareAction.withMedia(web);
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在分享");

        bottomSheetDialog = new BottomSheetDialog(this);
        View shareView = LayoutInflater.from(this).inflate(R.layout.share_dialog_view, null);
        ImageView mCloseImageView = shareView.findViewById(R.id.iv_close_share);

        LinearLayout weixinLayout = shareView.findViewById(R.id.layout_weixin);
        LinearLayout circleLayout = shareView.findViewById(R.id.layout_circle);
        LinearLayout qqLayout = shareView.findViewById(R.id.layout_qq_friends);
        LinearLayout qzoneLayout = shareView.findViewById(R.id.layout_qzone);
        weixinLayout.setOnClickListener(this);
        circleLayout.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        qzoneLayout.setOnClickListener(this);
        mCloseImageView.setOnClickListener(this);

        bottomSheetDialog.setContentView(shareView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_keep)
    void keepClick() {
        if (mUserInfo != null) {
            QueryNameInfo addParams = new QueryNameInfo();
            addParams.setUser_id(mUserInfo.getId());
            addParams.setType(mUserInfo.getLogin_type());
            addParams.setPhone(mUserInfo.getPhone());
            addParams.setOpenid(mUserInfo.getOpenid());
            addParams.setNameId(queryNameId);
            addKeepInfoPresenterImp.addKeep(addParams);
        }
    }

    @OnClick(R.id.iv_share)
    void share() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            dismissShareView();
            Toast.makeText(NameDetailActivity.this, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            dismissShareView();
            Toast.makeText(NameDetailActivity.this, "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            dismissShareView();
            Toast.makeText(NameDetailActivity.this, "取消分享", Toast.LENGTH_LONG).show();
        }
    };

    /*@Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("base_name_info")) {
            NameDetailWrapper nameDetailWrapper = messageEvent.getNameDetailWrapper();

            if (nameDetailWrapper != null && nameDetailWrapper.getWordInfo() != null && nameDetailWrapper.getWordInfo().size() > 0) {
                Logger.i("base name info--->" + JSON.toJSONString(nameDetailWrapper));
                wordList = nameDetailWrapper.getWordInfo();

                mNameWordListView.setLayoutManager(new GridLayoutManager(this, nameDetailWrapper.getWordInfo().size()));
                baseNameWordAdapter = new BaseNameWordAdapter(this, nameDetailWrapper.getWordInfo());
                mNameWordListView.setAdapter(baseNameWordAdapter);
                mNameScoreTv.setText(df.format(nameDetailWrapper.getWuge_score() > 100 ? 100 : nameDetailWrapper.getWuge_score()) + "");

                if (nameDetailWrapper.getIs_collect() == 0) {
                    mKeepIv.setImageResource(R.mipmap.is_nor_keep);
                    mKeepTv.setText("收藏");
                } else {
                    mKeepIv.setImageResource(R.mipmap.is_keep);
                    mKeepTv.setText("已收藏");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Logger.i("verticalOffset--->" + verticalOffset);
        if (verticalOffset == 0) {
            mSlidingLayout.setBackgroundResource(R.drawable.tab_sliding_bg);
        } else {
            mSlidingLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    public void dismissShareView() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        switch (v.getId()) {
            case R.id.layout_weixin:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();
                break;
            case R.id.layout_circle:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
                break;
            case R.id.layout_qq_friends:
                shareAction.setPlatform(SHARE_MEDIA.QQ).share();
                break;
            case R.id.layout_qzone:
                shareAction.setPlatform(SHARE_MEDIA.QZONE).share();
                break;
            case R.id.iv_close_share:
                dismissShareView();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
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
            if (tData instanceof AddKeepInfoRet && ((AddKeepInfoRet) tData).getCode() == Constants.SUCCESS) {
                if (((AddKeepInfoRet) tData).getCode() == Constants.SUCCESS) {
                    if (((AddKeepInfoRet) tData).getData().getStatus() == 0) {
                        mKeepIv.setImageResource(R.mipmap.is_nor_keep);
                        mKeepTv.setText("收藏");
                        Toasty.normal(this, "已取消").show();
                    } else {
                        mKeepIv.setImageResource(R.mipmap.is_keep);
                        mKeepTv.setText("已收藏");
                        Toasty.normal(this, "已收藏").show();
                    }

                    MessageEvent messageEvent = new MessageEvent("last_click_index");
                    messageEvent.setLastClickIndex(lastClickIndex);
                    messageEvent.setIsKeep(((AddKeepInfoRet) tData).getData().getStatus());
                    EventBus.getDefault().post(messageEvent);
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
