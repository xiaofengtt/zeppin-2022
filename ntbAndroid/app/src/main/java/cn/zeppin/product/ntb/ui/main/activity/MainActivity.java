package cn.zeppin.product.ntb.ui.main.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.geng.library.baseapp.AppManager;
import com.geng.library.baserx.RxBus;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.AppUtil;
import com.umeng.analytics.AnalyticsConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.UpdateVersionInfo;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.service.AlarmService;
import cn.zeppin.product.ntb.service.ApkDownloadService;
import cn.zeppin.product.ntb.ui.main.adapter.FragmentTabAdapter;
import cn.zeppin.product.ntb.ui.main.fragment.BankFragment;
import cn.zeppin.product.ntb.ui.main.fragment.FinanceFragment;
import cn.zeppin.product.ntb.ui.main.fragment.UserFragment;
import cn.zeppin.product.ntb.ui.main.model.GiftModel;
import cn.zeppin.product.ntb.ui.user.activity.CreateGestureActivity;
import cn.zeppin.product.ntb.ui.user.activity.CustomVersionDialogActivity;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import cn.zeppin.product.ntb.ui.user.presenter.CommonPresenter;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.AppConstant.GESTURE_SKIP_HIDE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_GIFT;
import static cn.zeppin.product.ntb.app.AppConstant.TAB_INDEX;

public class MainActivity extends BaseActivity<CommonPresenter, CommonModel> implements CommonContract.View {

    @Bind(R.id.main_radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.rb_bank)
    RadioButton rbBank;
    @Bind(R.id.rb_finance)
    RadioButton rbFinance;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.main_tab)
    LinearLayout mainTab;
    private FinanceFragment financeFragment;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentTabAdapter tabAdapter;
    private long exitTime = 0;

    //是否有新版本
    private boolean isNewVersion = false;

    private String uuid = AppApplication.getInstance().getUuid();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
        initFragments();
        //监听用户信息变化
        mRxManager.on(AppConstant.REFRESH_USER, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                initData();
            }
        });

        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstant.TO_MAIN);
        //注册广播接收
        registerReceiver(mReceiver, filter);

//        initData();

        getVersionInfo(false);
//        mainTab.setVisibility(View.VISIBLE);
        getWebmarketSwitch();
    }

    private void checkGift() {
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(new GiftModel().getNotActiveList(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<Gift>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<Gift> data) {
                            if (data.success()) {
                                Bundle bundle = new Bundle();
                                if (data.getData().getRedPacket().size() > 0 || data.getData().getCoupon().size() > 0) {
                                    bundle.putSerializable(INTENT_GIFT, data.getData());
                                    startActivity(JumpActivity.class, bundle);
                                }
                            }
                        }

                        @Override
                        protected void _onError(String message) {
                        }
                    }));
                } else {
                }
            }

            @Override
            protected void _onError(String message) {
            }
        }));
    }

    private void initData() {
        String uuid = AppApplication.getInstance().getUuid();
        if (!TextUtils.isEmpty(uuid)) {
            mPresenter.getUserInfo(uuid);
        }
    }

    private void initFragments() {
        financeFragment = new FinanceFragment();
        mFragmentList.add(new BankFragment());//银行理财
        mFragmentList.add(financeFragment);//我的持仓
        mFragmentList.add(new UserFragment());//个人中心

        tabAdapter = new FragmentTabAdapter(mFragmentList, mRadioGroup, this, R.id.fragment_content);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
            }
        });
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void sendCodeSuccess() {

    }

    @Override
    public void sendCodeFailed(String message) {

    }

    @Override
    public void returnMyBankList(List<MyBank> list) {

    }

    @Override
    public void returnFailed(String error) {

    }

    @Override
    public void getUserInfo(User user) {
        AppApplication.getInstance().setUser(user);
        RxBus.getInstance().post(AppConstant.REFRESH_USERFRAGMENT, true);//刷新用户信息
//        RxBus.getInstance().post(AppConstant.REFRESH_FINANCEFRAGMENT, true);//刷新持仓信息
        //得到当前用户的手势密码
        try {
            UserGesture userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", user.getPhone());
            if (userGesture == null) {
                //未设置密码时提示,设置手势密码
                Bundle bundle = new Bundle();
                bundle.putBoolean(GESTURE_SKIP_HIDE, false);
                startActivity(CreateGestureActivity.class, bundle);
            } else {
                if (userGesture.isOpen() && userGesture.getGesturePwd() == null) {
                //未设置密码时提示,设置手势密码
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(GESTURE_SKIP_HIDE, false);
                    startActivity(CreateGestureActivity.class, bundle);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播监听
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int index = intent.getIntExtra(TAB_INDEX, 0);
            AppManager.getAppManager().finishOtherActivity(MainActivity.class);
            if (index == AppConstant.FragmentIndex.BANK) {//银行理财
                rbBank.setChecked(true);
            } else if (index == AppConstant.FragmentIndex.FINANCE) {//我的持仓
                if (financeFragment.isAdded()) {//如果已经添加了
                    RxBus.getInstance().post(AppConstant.TO_FINANCE_TRANSACTION, true);//交易中
                }
                financeFragment.toFinanceTransaction();
                rbFinance.setChecked(true);
            }
        }
    };

    /**
     * 回退
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) { // System.currentTimeMillis()无论何时调用，肯定大于2000
                showToastCenter("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 判断是否应用商场设置的开关是否打开，不打开时，底部的tab和产品的详情页不可购买
     */
    private void getWebmarketSwitch() {
        final AppApplication app = AppApplication.getInstance();
        final String channel = AnalyticsConfig.getChannel(MainActivity.this);
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    String token = ApiConstants.getWebmarketSwitchToken(AppUtil.getVerCode(MainActivity.this), channel, resultData2.getData());
                    mRxManager.add(mModel.getWebmarketSwitch(token
                            , EncryptUtil.getBase64(channel)
                            , EncryptUtil.getBase64(AppUtil.getVerCode(MainActivity.this) + "")).subscribe(new RxSubscriber<ResultData2<Boolean>>(MainActivity.this, false) {
                        @Override
                        protected void _onNext(ResultData2<Boolean> resultData2) {
                            if (resultData2.success()) {
                                if (resultData2.getData()) {
                                    mainTab.setVisibility(View.VISIBLE);
                                    app.setWebmarketSwitch(true);
                                } else {
                                    mainTab.setVisibility(View.GONE);
                                    app.setWebmarketSwitch(false);
                                }
                            } else {
                                mainTab.setVisibility(View.GONE);
                                app.setWebmarketSwitch(false);
                            }
                        }

                        @Override
                        protected void _onError(String message) {
                            mainTab.setVisibility(View.GONE);
                            app.setWebmarketSwitch(false);
                        }
                    }));
                } else {
                    mainTab.setVisibility(View.GONE);
                    app.setWebmarketSwitch(false);
                }
            }

            @Override
            protected void _onError(String message) {
                mainTab.setVisibility(View.GONE);
                app.setWebmarketSwitch(false);
            }
        }));
    }


    /**
     * 版本更新
     */
    private void toVersionUpdate(UpdateVersionInfo updateVersionInfo, String timestamp) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", ApiConstants.getVersionInfoToken(AppUtil.getVerCode(MainActivity.this), timestamp));
        httpParams.put("version", EncryptUtil.getBase64(AppUtil.getVerCode(MainActivity.this) + ""));
        VersionParams.Builder builder = new VersionParams.Builder()
                .setRequestMethod(HttpRequestMethod.GET)
                .setRequestParams(httpParams)
                .setRequestUrl(ApiConstants.VERSION_INFO)
                .setService(ApkDownloadService.class);
        UpdateVersionInfo.NewVersionBean newVersion = updateVersionInfo.getNewVersion();

        builder.setTitle("升级到新版本 V" + newVersion.getVersionName());
        builder.setUpdateMsg(newVersion.getVersionDes());
        builder.setDownloadUrl(ApiConstants.RESOURCE_HOST + newVersion.getResourceUrl());
        //是否强制更新
        CustomVersionDialogActivity.isForceUpdate = updateVersionInfo.isFlagCompel();
        builder.setPauseRequestTime(0l);
//        builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
        //不强制重新下载
//        builder.setForceRedownload(false);
//        builder.setOnlyDownload(false);
        //通知栏显示
        if (updateVersionInfo.isFlagCompel()) {
            builder.setShowNotification(false);
            //是否显示下载进度框
            builder.setShowDownloadingDialog(true);
        } else {
            builder.setShowNotification(true);
            //是否显示下载进度框
            builder.setShowDownloadingDialog(false);
        }
        //自定义下载界面
        CustomVersionDialogActivity.isCustomDownloading = true;
        builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
        AllenChecker.startVersionCheck(MainActivity.this, builder.build());
    }

    /**
     * 判断版本更新
     */
    public void getVersionInfo(final boolean isLoadingDialog) {
        if (isLoadingDialog) {
            startProgressDialog("正在检查...");
        }
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    final String timstamp = resultData2.getData();
                    mRxManager.add(new CommonModel().getVersionInfo(
                            ApiConstants.getVersionInfoToken(AppUtil.getVerCode(MainActivity.this), timstamp)
                            , EncryptUtil.getBase64(AppUtil.getVerCode(MainActivity.this) + ""))
                            .subscribe(new RxSubscriber<ResultData2<UpdateVersionInfo>>(MainActivity.this, false) {
                                @Override
                                protected void _onNext(ResultData2<UpdateVersionInfo> resultData2) {
                                    if (resultData2.success()) {
                                        UpdateVersionInfo updateVersionInfo = resultData2.getData();
                                        if (updateVersionInfo.isFlagUpdate()) {//有最新
                                            isNewVersion = true;
                                            //判断是否需要强制
                                            toVersionUpdate(updateVersionInfo, timstamp);
                                        } else {
                                            AllenChecker.cancelMission();
                                            if (isLoadingDialog) {
                                                showToastCenter(getString(R.string.current_last_version));
                                            }
                                        }
                                    } else {
                                        AllenChecker.cancelMission();
                                        if (isLoadingDialog) {
                                            showToastCenter(resultData2.getMessage());
                                        }
                                    }
                                    if (isLoadingDialog) {
                                        stopProgressDialog();
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    if (isLoadingDialog) {
                                        stopProgressDialog();
                                        showToastCenter(message);
                                    }
                                    AllenChecker.cancelMission();
                                }
                            }));
                } else {
                    if (isLoadingDialog) {
                        stopProgressDialog();
                        showToastCenter(resultData2.getMessage());
                    }
                    AllenChecker.cancelMission();
                }
            }

            @Override
            protected void _onError(String message) {
                if (isLoadingDialog) {
                    stopProgressDialog();
                    showToastCenter(message);
                }
                AllenChecker.cancelMission();
            }
        }));
    }

    public boolean isNewVersion() {
        return isNewVersion;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if (!TextUtils.isEmpty(AppApplication.getInstance().getUuid())) {
            checkGift();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
