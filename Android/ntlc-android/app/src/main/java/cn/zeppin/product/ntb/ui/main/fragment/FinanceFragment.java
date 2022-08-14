package cn.zeppin.product.ntb.ui.main.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.geng.library.baserx.RxBus;
import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.ToastUitl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.base.BaseFragment;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserControl;
import cn.zeppin.product.ntb.bean.UserFinance;
import cn.zeppin.product.ntb.ui.finance.activity.Recharge2Activity;
import cn.zeppin.product.ntb.ui.finance.activity.WithdrawalActivity;
import cn.zeppin.product.ntb.ui.finance.adapter.TabPagerAdapter;
import cn.zeppin.product.ntb.ui.finance.fragment.FinishFragment;
import cn.zeppin.product.ntb.ui.finance.fragment.HoldFragment;
import cn.zeppin.product.ntb.ui.finance.fragment.TransactionFragment;
import cn.zeppin.product.ntb.ui.user.activity.CertificationActivity;
import cn.zeppin.product.ntb.ui.user.activity.LoginActivity;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.app.Activity.RESULT_OK;
import static cn.zeppin.product.ntb.R.id.app_bar;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_LOGIN;

/**
 * des:我的持仓
 * Created by xsf
 * on 2016.09.17:07
 */
public class FinanceFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_topDes)
    TextView tvTopDes;
    @Bind(R.id.tv_topValue)
    TextView tvTopValue;
    @Bind(R.id.ll_totalAmount)
    LinearLayout llTotalAmount;
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    @Bind(R.id.tv_earningsDes)
    TextView tvEarningsDes;
    @Bind(R.id.tv_accountBalance)
    TextView tvAccountBalance;
    @Bind(R.id.tv_recharge)
    TextView tvRecharge;
    @Bind(R.id.tv_withdrawal)
    TextView tvWithdrawal;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(app_bar)
    AppBarLayout appBar;
    @Bind(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    //    @Bind(R.id.ll_content)
//    LinearLayout llContent;
    @Bind(R.id.btn_login)
    Button btnLogin;
    //    @Bind(R.id.cv_bank)
//    CardView cvBank;
    @Bind(R.id.ll_unLogin)
    LinearLayout llUnLogin;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_tab)
    LinearLayout llTab;
    @Bind(R.id.finance_PtrClassicFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.cb_display)
    CheckBox cbDisplay;
    private UserFinance userFinance;
    //默认月收益
    private boolean isBuyMonth = true;

    private List<Fragment> mFragments;
    private TabPagerAdapter mAdapter;
    private String[] titles = {"持有中", "交易中", "已完成"};
    private String uuid;
    private boolean isTransaction = false;


    private CollapsingToolbarLayoutState state;

    private boolean isShow = true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private User user;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_finance;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        user = AppApplication.getInstance().getUser();
        initFragment();
        tvRecharge.setOnClickListener(this);//充值
        tvWithdrawal.setOnClickListener(this);//提现
        ivSwitch.setOnClickListener(this);//切换
        btnLogin.setOnClickListener(this);//立即登录
        cbDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showOrHideAmount(isChecked);
            }
        });


        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setHeaderView(View.inflate(getContext(), R.layout.loading_header_pull_view, null));
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, coordinatorLayout, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //顶部下拉刷新
                if (TextUtils.isEmpty(AppApplication.getInstance().getUuid())) {
                    mPtrFrame.refreshComplete();
                } else {
                    stopProgressDialog();
                    initData();
//                    RxBus.getInstance().post(AppConstant.TO_FINANCE, true);//刷新
                }
            }
        });

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    mPtrFrame.setEnabled(true);
                } else {
                    mPtrFrame.setEnabled(false);
                }
            }
        });

        //监听信息变化
        mRxManager.on(AppConstant.TO_FINANCE_TRANSACTION, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                viewPager.setCurrentItem(1);//交易中
//                RxBus.getInstance().post(AppConstant.REFRESH_FINANCE_TRANSACTION, true);
            }
        });

        mRxManager.on(AppConstant.LOGIN_OUT, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                changeLogoutView();
            }
        });

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        tvTitle.setText("");//
//                        cbDisplay.setVisibility(View.VISIBLE);
                        changeCbDisplay(true);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        tvTitle.setText("我的持仓");//
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
//                        cbDisplay.setVisibility(View.GONE);
                        changeCbDisplay(false);
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                        changeCbDisplay(false);
                    }
                }
            }
        });
    }

    private void initData() {
        uuid = AppApplication.getInstance().getUuid();
        if (TextUtils.isEmpty(uuid)) {
            changeLogoutView();
        } else {
            llUnLogin.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            llTab.setVisibility(View.VISIBLE);
            changeCbDisplay(true);
            getUserFinanceInfo();
            //得到当前用户的设置
            try {
                user = AppApplication.getInstance().getUser();
                if (user != null) {
                    UserControl userControl = (UserControl) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserControl.class, "user", user.getPhone());
                    if (userControl != null) {
                        isShow = userControl.isShow();
                        cbDisplay.setChecked(isShow);
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

    }

    private void changeCbDisplay(boolean isShow) {
        if (isShow && !TextUtils.isEmpty(AppApplication.getInstance().getUuid()) && TextUtils.isEmpty(tvTitle.getText())) {
            cbDisplay.setVisibility(View.VISIBLE);
        } else {
            cbDisplay.setVisibility(View.GONE);
        }
    }

    private void changeLogoutView() {
        llUnLogin.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        llTab.setVisibility(View.GONE);
        tvTopValue.setText("****");
        tvAccountBalance.setText("****");
        mPtrFrame.refreshComplete();
        cbDisplay.setVisibility(View.GONE);
        uuid = null;
    }

    private void initFragment() {
        //页面，数据源
        mFragments = new ArrayList<>();

        mFragments.add(new HoldFragment());
        //打开APP直接进入购买，购买成功进入"我的持仓"--"交易中"，这是fragment是第一次加载，需要直接传值
        mFragments.add(new TransactionFragment());
        mFragments.add(new FinishFragment());
        //ViewPager的适配器
        mAdapter = new TabPagerAdapter(getFragmentManager(), mFragments, titles);
        viewPager.setAdapter(mAdapter);
        //绑定
        tabLayout.setViewPager(viewPager);

        if (isTransaction) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!isTransaction) {
                    switch (position) {
                        case 0://持有中
                            RxBus.getInstance().post(AppConstant.REFRESH_FINANCE_HOLD, true);
                            break;
                        case 1://交易中
                            RxBus.getInstance().post(AppConstant.REFRESH_FINANCE_TRANSACTION, true);
                            break;
                        case 2://已完成
                            RxBus.getInstance().post(AppConstant.REFRESH_FINANCE_FINISH, true);
                            break;
                    }
                } else {
                    isTransaction = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        setupTabIcons();
//        ContextUtils.setIndicator(getActivity(), tabLayout, 20, 20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recharge://充值
                if (isNormal()) {
                    startActivity(Recharge2Activity.class);
                }

                break;
            case R.id.tv_withdrawal://提现
                if (isNormal()) {
                    startActivity(WithdrawalActivity.class);
                }
                break;
            case R.id.iv_switch://切换（月收益/日收益）
                if (isNormal()) {
                    isBuyMonth = !isBuyMonth;
                    toSwitch(isBuyMonth);
                }
                break;
            case R.id.btn_login://立即登录
                startActivityForResult(LoginActivity.class, REQUEST_CODE_LOGIN);
                break;
            default:
                break;
        }
    }

    /**
     * 是否已登录已认证
     *
     * @return
     */
    private boolean isNormal() {
        if (TextUtils.isEmpty(uuid)) { //未登录
            startActivityForResult(LoginActivity.class, REQUEST_CODE_LOGIN);
            return false;
        }
        user = AppApplication.getInstance().getUser();
        //实名认证判断
        if (user != null && !user.isRealnameAuthFlag()) {
            startProgressDialog(getResources().getString(R.string.to_certification_toast));
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    stopProgressDialog();
                    /**
                     *要执行的操作
                     */
                    startActivity(CertificationActivity.class);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);//2秒后执行
            return false;
        }
        return true;
    }

    private void toSuccess(UserFinance userFinance) {
        if (userFinance != null) {
            this.userFinance = userFinance;

            if (isShow) {
                tvAccountBalance.setText(userFinance.getAccountBalance());//账户余额
            } else {
                tvAccountBalance.setText("****");
            }
//            tvTotalInvest.setText(userFinance.getTotalInvest());//理财金额
            toSwitch(isBuyMonth);
            user = AppApplication.getInstance().getUser();
            if (user != null) {
                user.setAccountBalance(userFinance.getAccountBalance().replace(",", ""));
            }

        }
    }

    /**
     * 切换（月收益/日收益）
     *
     * @param isBuyMonth
     */
    private void toSwitch(boolean isBuyMonth) {
        if (userFinance != null) {
            if (isBuyMonth) {
                if (isShow) {
                    tvTopValue.setText(userFinance.getTotalAmount());//总资产
                } else {
                    tvTopValue.setText("****");
                }
                tvTopDes.setText("总资产(元)");
            } else {
                if (isShow) {
                    tvTopValue.setText(userFinance.getTotalReturn());//总收益
                } else {
                    tvTopValue.setText("****");
                }
                tvTopDes.setText("总收益(元)");
            }
        }
    }

    private void toFailed(String error) {
        ToastUitl.showToastCenter(error);
    }

    private void getUserFinanceInfo() {
        stopProgressDialog();
        startProgressDialog();

        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    String uuid = AppApplication.getInstance().getUuid();
                    mRxManager.add(getUserFinanceInfo(uuid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<UserFinance>>(getActivity(), false) {
                                @Override
                                protected void _onNext(ResultData2<UserFinance> resultData) {
                                    stopProgressDialog();
                                    if (resultData.success()) {
                                        toSuccess(resultData.getData());
                                    } else {
                                        toFailed(resultData.getMessage());
                                    }
                                    mPtrFrame.refreshComplete();
                                }

                                @Override
                                protected void _onError(String message) {
                                    stopProgressDialog();
                                    mPtrFrame.refreshComplete();
                                    toFailed(message);
                                }
                            }));
                } else {
                    stopProgressDialog();
                    toFailed(resultData2.getMessage());
                    mPtrFrame.refreshComplete();
                }
            }

            @Override
            protected void _onError(String message) {
                stopProgressDialog();
                mPtrFrame.refreshComplete();
                toFailed(message);
            }
        }));
    }

    private Observable<ResultData2<UserFinance>> getUserFinanceInfo(String uuid, String token) {
        return Api.getDefault().getFinanceUser(uuid, token)
                .map(new Func1<ResultData2<UserFinance>, ResultData2<UserFinance>>() {
                    @Override
                    public ResultData2<UserFinance> call(ResultData2<UserFinance> userFinanceResultData) {
                        return userFinanceResultData;
                    }
                }).compose(RxSchedulers.<ResultData2<UserFinance>>io_main());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOGIN) {
            //登录成功
//            initData();
        }
    }

    public void toFinanceTransaction() {
        //交易中
        isTransaction = true;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(AppApplication.getInstance().getUuid())) {
            stopProgressDialog();
            initData();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void showOrHideAmount(final boolean isShow) {
        user = AppApplication.getInstance().getUser();
        if (user != null) {
            try {
                UserControl userControl = (UserControl) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserControl.class, "user", user.getPhone());
                if (userControl != null) {
                    AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //先查找后得到UserGesture对象
                            UserControl uControl = AppApplication.REALM_INSTANCE.where(UserControl.class).equalTo("user", user.getPhone()).findFirst();
                            uControl.setShow(isShow);
                        }
                    });
                } else {
                    userControl = new UserControl(user.getPhone(), isShow);
                    RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).add(userControl);
                }
                this.isShow = isShow;
                if (isShow) {
                    tvAccountBalance.setText(userFinance.getAccountBalance());//账户余额
                } else {
                    tvAccountBalance.setText("****");
                }
                toSwitch(isBuyMonth);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

}
