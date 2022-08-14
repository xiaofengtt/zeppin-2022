package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.NetWorkUtils;
import com.geng.library.commonutils.TimeUtil;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.TradingRecordDetail;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BILL_ID;


public class TradingRecordDetailActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_tradingType)
    TextView tvTradingType;
    @Bind(R.id.tv_typeCN)
    TextView tvTypeCN;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_orderType)
    TextView tvOrderType;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_accountBalance)
    TextView tvAccountBalance;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_orderNum)
    TextView tvOrderNum;
    @Bind(R.id.tv_createTime)
    TextView tvCreateTime;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.ll_content)
    LinearLayout llContent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_trading_record_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("交易详情");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (NetWorkUtils.isNetConnected(this)) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String billId = bundle.getString(INTENT_BILL_ID);
                getTradingRecordInfoPresenter(AppApplication.getInstance().getUuid(), billId);
            }
        } else {
            showToastCenter(getResources().getString(R.string.no_net));
        }
    }

    private void toSuccess(TradingRecordDetail data) {
        if (data != null) {
            tvTypeCN.setText(data.getTypeCN());

            if (data.isPriceflag()) {//收入
                tvTradingType.setText("收");
                tvTradingType.setBackgroundResource(R.drawable.record_income);
                tvPrice.setText("+" + data.getPrice());//交易金额
                tvPrice.setTextColor(getResources().getColor(R.color.color_E05B59));
            } else {
                tvTradingType.setText("支");
                tvTradingType.setBackgroundResource(R.drawable.record_pay);
                tvPrice.setText("-" + data.getPrice());//交易金额
                tvPrice.setTextColor(getResources().getColor(R.color.trading_record_pay));
            }

            tvOrderType.setText("交易方式：" + data.getOrderTypeCN());
            tvStatus.setText("交易状态：" + data.getStatusCN());
            tvAccountBalance.setText("本次余额：" + data.getAccountBalanceCN());
            tvType.setText("类型：" + data.getTypeCN());
            tvOrderNum.setText("订单号：" + data.getOrderNum());
            tvCreateTime.setText("交易时间：" + TimeUtil.formatTimeInMillis(data.getCreatetime(), TimeUtil.sdf2));
            tvRemark.setText("备注：" + data.getRemark());

            llContent.setVisibility(View.VISIBLE);
        }
    }


    private void getTradingRecordInfoPresenter(final String uuid, final String billid) {
        startProgressDialog();
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(getTradingRecordInfoModel(uuid, billid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<TradingRecordDetail>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<TradingRecordDetail> resultData2) {
                                    if (resultData2.success()) {
                                        toSuccess(resultData2.getData());
                                    } else {
                                        showToastCenter(resultData2.getMessage());
                                    }
                                    stopProgressDialog();
                                }

                                @Override
                                protected void _onError(String message) {
                                    showToastCenter(message);
                                    stopProgressDialog();
                                }
                            }));

                } else {
                    stopProgressDialog();
                    showToastCenter(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                showToastCenter(message);
                stopProgressDialog();
            }
        }));
    }

    private Observable<ResultData2<TradingRecordDetail>> getTradingRecordInfoModel(String uuid, String billid, String token) {
        return Api.getDefault().getTradingRecordInfo(uuid, billid, token)
                .map(new Func1<ResultData2<TradingRecordDetail>, ResultData2<TradingRecordDetail>>() {
                    @Override
                    public ResultData2<TradingRecordDetail> call(ResultData2<TradingRecordDetail> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<TradingRecordDetail>>io_main());
    }
}
