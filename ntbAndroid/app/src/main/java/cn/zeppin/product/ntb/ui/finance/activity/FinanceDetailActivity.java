package cn.zeppin.product.ntb.ui.finance.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.NetWorkUtils;
import com.geng.library.commonutils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.FinanceDetail;
import cn.zeppin.product.ntb.bean.FinanceHistory;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.bank.activity.BankDetailActivity;
import cn.zeppin.product.ntb.ui.finance.adapter.BuyHistoryAdapter;
import cn.zeppin.product.ntb.ui.main.activity.WebActivity;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.R.id.tv_price;
import static cn.zeppin.product.ntb.app.ApiConstants.PDFFILE;
import static cn.zeppin.product.ntb.app.ApiConstants.PDFNAME;
import static cn.zeppin.product.ntb.app.ApiConstants.PRODUCT_BUY_AGREEMENT_NAME;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_TITLE;

public class FinanceDetailActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_priceTop)
    TextView tvPriceTop;
    @Bind(R.id.iv_finish)
    ImageView ivFinish;
    @Bind(R.id.tv_productName)
    TextView tvProductName;
    @Bind(R.id.tv_term)
    TextView tvTerm;
    @Bind(tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_rate_des)
    TextView tvRateDes;
    @Bind(R.id.tv_rate)
    TextView tvRate;
    @Bind(R.id.tv_payTime)
    TextView tvPayTime;
    @Bind(R.id.tv_valueDate)
    TextView tvValueDate;
    @Bind(R.id.tv_maturityDate)
    TextView tvMaturityDate;
    @Bind(R.id.tv_stage)
    TextView tvStage;
    @Bind(R.id.tv_agreementName)
    TextView tvAgreementName;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.ll_net_content)
    LinearLayout llNetContent;
    @Bind(R.id.iv_show)
    ImageView ivShow;
    @Bind(R.id.history_recyclerView)
    RecyclerView historyRecyclerView;
    @Bind(R.id.ll_buy_price)
    LinearLayout llBuyPrice;
    //持仓的id
    private String finance_uuid = "";
    private FinanceDetail data;
    private BuyHistoryAdapter mAdapter;
    private List<FinanceHistory> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_finance_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("购买详情");
        ivBack.setOnClickListener(this);
        tvAgreementName.setOnClickListener(this);
        tvRefresh.setOnClickListener(this);
        llBuyPrice.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            finance_uuid = bundle.getString(AppConstant.INTENT_FINANCE_UUID);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        historyRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BuyHistoryAdapter(mList);
        historyRecyclerView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        startProgressDialog();
        if (NetWorkUtils.isNetConnected(this)) {
            getFinanceInfoPresenter(AppApplication.getInstance().getUuid(), finance_uuid);
        } else {
            showNetErrerView(true);
        }
    }


    private void toSuccess(FinanceDetail data) {
        if (data != null) {
            this.data = data;
            tvProductName.setText("【" + data.getBankName() + "】" + data.getProductName());//产品名称
            if (data.isFlagBuy()) {//有效
                tvProductName.setTextColor(getResources().getColor(R.color.color_80_2F6EB7));
                tvProductName.setOnClickListener(this);
            } else {
                tvProductName.setTextColor(getResources().getColor(R.color.alpha_80_black));
            }

            tvTerm.setText(data.getTerm() + "天");//产品期限
            tvPriceTop.setText(data.getPriceCN() + "");
            tvPrice.setText(data.getPriceCN() + "元");//购买金额
            tvPayTime.setText(TimeUtil.formatTimeInMillis(data.getPaytime(), TimeUtil.sdf2));//购买时间

            tvValueDate.setText(data.getValueDate());//起息日
            tvMaturityDate.setText(data.getMaturityDate());//到期日

            //交易状态
            if (ApiConstants.Finance.PROFIT.equals(data.getStage())) {//收益中
                setStageDrawables(R.drawable.icon_profit);
                ivFinish.setVisibility(View.GONE);
                tvRate.setText(data.getTargetAnnualizedReturnRate() + "%");//预期收益率
                tvRateDes.setText("预期收益率");
            } else if (ApiConstants.Finance.CONFIRMING.equals(data.getStage())) {//确认中
                setStageDrawables(R.drawable.icon_confirming);
                ivFinish.setVisibility(View.GONE);
                tvRate.setText(data.getTargetAnnualizedReturnRate() + "%");//预期收益率
                tvRateDes.setText("预期收益率");
            } else if (ApiConstants.Finance.BALANCE.equals(data.getStage())) {//还款中
                setStageDrawables(R.drawable.icon_balance);
                ivFinish.setVisibility(View.GONE);
                tvRate.setText(data.getTargetAnnualizedReturnRate() + "%");//预期收益率
                tvRateDes.setText("预期收益率");
            } else if (ApiConstants.Finance.FINISHED.equals(data.getStage())) {//已完成
                setStageDrawables(R.drawable.icon_finished);
                ivFinish.setVisibility(View.VISIBLE);
                tvRate.setText(data.getRealReturnRate() + "%");//实际收益率
                tvRateDes.setText("实际收益率");
            }

            tvStage.setText(data.getStageCN());//交易状态
            tvAgreementName.setText(data.getAgreementName());//购买协议
            mList.clear();
            mList.addAll(data.getAccountHistory());
            mAdapter.notifyDataSetChanged();
            showNetErrerView(false);
        }
    }

    private void setStageDrawables(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tvStage.setCompoundDrawables(drawable, null, null, null);//画在右边
    }

    private void toFailed(String message) {
        showNetErrerView(false);
    }

    private void getFinanceInfoPresenter(final String uuid, final String financialUuid) {
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(getFinanceInfoModel(uuid, financialUuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(
                            new RxSubscriber<ResultData2<FinanceDetail>>(FinanceDetailActivity.this, false) {
                                @Override
                                protected void _onNext(ResultData2<FinanceDetail> resultData) {
                                    if (resultData.success()) {
                                        toSuccess(resultData.getData());
                                    } else {
                                        toFailed(resultData.getMessage());
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    toFailed(message);
                                }
                            }));
                } else {
                    toFailed(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                toFailed(message);
            }
        }));

    }

    private Observable<ResultData2<FinanceDetail>> getFinanceInfoModel(String uuid, String financialUuid, String token) {
        return Api.getDefault().getFinanceInfo(uuid, financialUuid, token)
                .map(new Func1<ResultData2<FinanceDetail>, ResultData2<FinanceDetail>>() {
                    @Override
                    public ResultData2<FinanceDetail> call(ResultData2<FinanceDetail> resultData) {
                        return resultData;
                    }
                }).compose(RxSchedulers.<ResultData2<FinanceDetail>>io_main());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_refresh://刷新
                initData();
                break;
            case R.id.tv_productName://产品名称
                if (data != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstant.PRODUCT_UUID, data.getProduct());
                    startActivity(BankDetailActivity.class, bundle);
                }
                break;
            case R.id.tv_agreementName://牛投理财定向委托投资管理协议
                if (data != null) {
                    if (!TextUtils.isEmpty(data.getAgreementUrl())) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstant.INTENT_URL, ApiConstants.PDF_HOST
                                .replace(PDFFILE, ".." + data.getAgreementUrl())
                                .replace(PDFNAME, PRODUCT_BUY_AGREEMENT_NAME));
                        bundle.putString(INTENT_TITLE, PRODUCT_BUY_AGREEMENT_NAME);
                        startActivity(WebActivity.class, bundle);
                    }
                }
                break;
            case R.id.ll_buy_price://显示/隐藏 购买记录
                if (historyRecyclerView.getVisibility() == View.VISIBLE) {
                    //隐藏
                    historyRecyclerView.setVisibility(View.GONE);
                    ivShow.setImageDrawable(getResources().getDrawable(R.drawable.icon_grey_down));
                } else {
                    historyRecyclerView.setVisibility(View.VISIBLE);
                    ivShow.setImageDrawable(getResources().getDrawable(R.drawable.icon_grey_up));
                }
                break;
        }

    }

    private void showNetErrerView(boolean isShow) {
        if (isShow) {
            llNetContent.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        } else {
            llNetContent.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);
        }
        stopProgressDialog();
    }
}
