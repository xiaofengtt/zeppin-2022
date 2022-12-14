package cn.zeppin.product.ntb.ui.bank.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.commonutils.NetWorkUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.ProductDetail;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.activity.CertificationActivity;
import cn.zeppin.product.ntb.ui.user.activity.LoginActivity;

import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.UNINVEST;

public class BankDetailAllActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_scode)
    TextView tvScode;
    @Bind(R.id.tv_currencyType)
    TextView tvCurrencyType;
    @Bind(R.id.tv_TARRate)
    TextView tvTARRate;
    @Bind(R.id.tv_term)
    TextView tvTerm;
    @Bind(R.id.tv_totalAmount)
    TextView tvTotalAmount;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_riskLevelCN)
    TextView tvRiskLevel;
    @Bind(R.id.tv_paymentType)
    TextView tvPaymentType;
    @Bind(R.id.tv_minInvestAmount)
    TextView tvMinInvestAmount;
    @Bind(R.id.tv_maxInvestAmount)
    TextView tvMaxInvestAmount;
    @Bind(R.id.tv_minInvestAmountAdd)
    TextView tvMinInvestAmountAdd;
    @Bind(R.id.tv_collectStarttime)
    TextView tvCollectStarttime;
    @Bind(R.id.tv_collectEndtime)
    TextView tvCollectEndtime;
    @Bind(R.id.tv_recordDate)
    TextView tvRecordDate;
    @Bind(R.id.tv_valueDate)
    TextView tvValueDate;
    @Bind(R.id.tv_maturityDate)
    TextView tvMaturityDate;
    @Bind(R.id.tv_managementFee)
    TextView tvManagementFee;
    @Bind(R.id.tv_investScope)
    TextView tvInvestScope;
    @Bind(R.id.tv_revenueFeature)
    TextView tvRevenueFeature;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_subscribeFee)
    TextView tvSubscribeFee;
    @Bind(R.id.tv_purchaseFee)
    TextView tvPurchaseFee;
    @Bind(R.id.tv_redemingFee)
    TextView tvRedemingFee;
    @Bind(R.id.tv_custodyFee)
    TextView tvCustodyFee;
    @Bind(R.id.tv_networkFee)
    TextView tvNetworkFee;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.ll_subscribeFee)
    LinearLayout llSubscribeFee;
    @Bind(R.id.ll_purchaseFee)
    LinearLayout llPurchaseFee;
    @Bind(R.id.ll_redemingFee)
    LinearLayout llRedemingFee;
    @Bind(R.id.ll_custodyFee)
    LinearLayout llCustodyFee;
    @Bind(R.id.ll_networkFee)
    LinearLayout llNetworkFee;
    @Bind(R.id.ll_managementFee)
    LinearLayout llManagementFee;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.tv_scodeTop)
    TextView tvScodeTop;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    private ProductDetail mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_detail_all;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("???????????????");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //??????????????????????????????????????????????????????????????????????????????tab?????????????????????????????????
//        if(AppApplication.getInstance().isWebmarketSwitch()){
//            btnBuy.setVisibility(View.VISIBLE);
//        }else {
//            btnBuy.setVisibility(View.GONE);
//        }

        startProgressDialog();
        if (!NetWorkUtils.isNetConnected(this)) {
            isNetErrorView(true);
        } else {
            initData();
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//??????
                    //????????????
                    if (TextUtils.isEmpty(AppApplication.getInstance().getUuid())) {
                        startActivity(LoginActivity.class);
                        return;
                    }
                    User user = AppApplication.getInstance().getUser();
                    //??????????????????
                    if (user != null && !user.isRealnameAuthFlag()) {
                        startProgressDialog(getResources().getString(R.string.to_certification_toast));
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                                /**
                                 *??????????????????
                                 */
                                startActivity(CertificationActivity.class);
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 2000);//2????????????
                        return;
                    }
                    if (mData != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AppConstant.INTENT_PRODUCT_DETAIL, mData);
                        startActivity(ProductBuyActivity.class, bundle);
                    }
                }
            });
        }

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mData = (ProductDetail) bundle.getSerializable(AppConstant.INTENT_PRODUCT_DETAIL);
            Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + mData.getIconColorUrl()).into(ivBankIcon);

//            if (totalRaise <= 0) {
//                btnBuy.setEnabled(false);
//                btnBuy.setText("????????????");
//            } else {
//                btnBuy.setEnabled(true);
//                btnBuy.setText("??????");
//            }
            //???????????????
            if (mData.isFlagBuy()) {
//                btnBuy.setEnabled(true);
                //??????????????????????????????????????????????????????????????????????????????
                if (UNINVEST.equals(mData.getStage())) {
                    btnBuy.setEnabled(false);
                    btnBuy.setText("????????????");
                } else {
                    btnBuy.setEnabled(true);
                    btnBuy.setText("??????");
                }
            } else {
                btnBuy.setEnabled(false);
            }

            tvName.setText(mData.getName());
            tvBankName.setText("???" + mData.getCustodianCN() + "???");
            tvScodeTop.setText("??????" + mData.getScode());
            //????????????
            tvScode.setText(mData.getScode());//????????????
            tvCurrencyType.setText(mData.getCurrencyTypeCN());//????????????
            tvTARRate.setText(mData.getTargetAnnualizedReturnRate() + "%");//?????????????????????
            tvTerm.setText(mData.getTerm() + "???");//????????????
            tvTotalAmount.setText(mData.getTotalAmount() + "??????");//????????????
            tvType.setText(mData.getTypeCN());//????????????
            tvRiskLevel.setText(mData.getRiskLevelCN());//????????????
            tvPaymentType.setText(mData.getPaymentTypeCN());//??????????????????
            //????????????
            tvMinInvestAmount.setText(mData.getMinInvestAmountNum() + mData.getMinInvestAmountCN() + "???");//??????????????????
            tvMaxInvestAmount.setText(mData.getMaxInvestAmountNum() + mData.getMaxInvestAmountCN() + "???");//??????????????????
            tvMinInvestAmountAdd.setText(mData.getMinInvestAmountAddNum() + mData.getMinInvestAmountAddCN() + "???");//????????????
            tvCollectStarttime.setText(mData.getCollectStarttimeCN());//?????????????????????
            tvCollectEndtime.setText(mData.getCollectEndtimeCN());//?????????????????????
            tvRecordDate.setText(mData.getRecordDateCN());//??????????????????
            tvValueDate.setText(mData.getValueDateCN());//?????????
            tvMaturityDate.setText(mData.getMaturityDateCN());//?????????
            //-------????????????

            //????????????
            if ("0.00".equals(mData.getSubscribeFee())) {
                llSubscribeFee.setVisibility(View.GONE);
            } else {
                llSubscribeFee.setVisibility(View.VISIBLE);
                tvSubscribeFee.setText(mData.getSubscribeFee() + "%");
            }
            //????????????
            if ("0.00".equals(mData.getPurchaseFee())) {
                llPurchaseFee.setVisibility(View.GONE);
            } else {
                llPurchaseFee.setVisibility(View.VISIBLE);
                tvPurchaseFee.setText(mData.getPurchaseFee() + "%");//????????????
            }
            //?????????
            if ("0.00".equals(mData.getRedemingFee())) {
                llRedemingFee.setVisibility(View.GONE);
            } else {
                llRedemingFee.setVisibility(View.VISIBLE);
                tvRedemingFee.setText(mData.getRedemingFee() + "%");
            }
            //??????????????????
            if ("0.00".equals(mData.getNetworkFee())) {
                llNetworkFee.setVisibility(View.GONE);
            } else {
                llNetworkFee.setVisibility(View.VISIBLE);
                tvNetworkFee.setText(mData.getNetworkFee() + "%");
            }
            //????????????
            if ("0.00".equals(mData.getCustodyFee())) {
                llCustodyFee.setVisibility(View.GONE);
            } else {
                llCustodyFee.setVisibility(View.VISIBLE);
                tvCustodyFee.setText(mData.getCustodyFee() + "%");
            }
            //????????????
            if ("0.00".equals(mData.getManagementFee())) {
                llManagementFee.setVisibility(View.GONE);
            } else {
                llManagementFee.setVisibility(View.VISIBLE);
                tvManagementFee.setText(mData.getManagementFee() + "%");
            }
            //????????????
            tvInvestScope.setText(mData.getInvestScopeWeb());//????????????
            tvRevenueFeature.setText(mData.getRevenueFeatureWeb());//????????????
            //????????????
            if (TextUtils.isEmpty(mData.getRemarkWeb())) {
                tvRemark.setText("??????");
            } else {
                tvRemark.setText(mData.getRemarkWeb());
            }

            isNetErrorView(false);
        }
    }

    private void isNetErrorView(boolean isError) {
        if (isError) {
            showToastCenter(getResources().getString(R.string.no_net));
        } else {
            nestedScrollView.setVisibility(View.VISIBLE);
//            btnBuy.setVisibility(View.VISIBLE);
            //??????????????????????????????????????????????????????????????????????????????tab?????????????????????????????????
            if (AppApplication.getInstance().isWebmarketSwitch()) {
                btnBuy.setVisibility(View.VISIBLE);
            } else {
                btnBuy.setVisibility(View.GONE);
            }
        }
        stopProgressDialog();
    }
}
