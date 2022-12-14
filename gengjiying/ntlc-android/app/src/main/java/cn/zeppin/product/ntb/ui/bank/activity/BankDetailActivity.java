package cn.zeppin.product.ntb.ui.bank.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.socialize.UMShareAPI;

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
import cn.zeppin.product.ntb.ui.bank.contract.ProductDetailContract;
import cn.zeppin.product.ntb.ui.bank.model.ProductDetailModel;
import cn.zeppin.product.ntb.ui.bank.presenter.ProduceDetailPresenter;
import cn.zeppin.product.ntb.ui.user.activity.CertificationActivity;
import cn.zeppin.product.ntb.ui.user.activity.LoginActivity;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.BALANCE;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.COLLECT;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.FINISHED;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.PROFIT;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.UNINVEST;
import static cn.zeppin.product.ntb.app.ApiConstants.ProductStage.UNSTART;


public class BankDetailActivity extends BaseActivity<ProduceDetailPresenter, ProductDetailModel> implements ProductDetailContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.tv_scode)
    TextView tvScode;
    @Bind(R.id.tv_TARRate)
    TextView tvTARRate;
    @Bind(R.id.tv_term)
    TextView tvTerm;
    @Bind(R.id.tv_totalRaise)
    TextView tvTotalRaise;
    @Bind(R.id.seekBar_balance)
    SeekBar seekBarBalance;
    @Bind(R.id.tv_riskLevel)
    TextView tvRiskLevel;
    @Bind(R.id.tv_guaranteeStatusCN)
    TextView tvGuaranteeStatusCN;
    @Bind(R.id.tv_minInvestAmountNum)
    TextView tvMinInvestAmountNum;
    @Bind(R.id.iv_start)
    ImageView ivStart;
    @Bind(R.id.view_start_right)
    View viewStartRight;
    @Bind(R.id.view_end_left)
    View viewEndLeft;
    @Bind(R.id.iv_end)
    ImageView ivEnd;
    @Bind(R.id.view_end_right)
    View viewEndRight;
    @Bind(R.id.view_value_left)
    View viewValueLeft;
    @Bind(R.id.iv_value)
    ImageView ivValue;
    @Bind(R.id.view_value_right)
    View viewValueRight;
    @Bind(R.id.view_maturity_left)
    View viewMaturityLeft;
    @Bind(R.id.iv_maturity)
    ImageView ivMaturity;
    @Bind(R.id.tv_start_date)
    TextView tvStartDate;
    @Bind(R.id.tv_end_date)
    TextView tvEndDate;
    @Bind(R.id.tv_value_date)
    TextView tvValueDate;
    @Bind(R.id.tv_maturity_date)
    TextView tvMaturityDate;
    @Bind(R.id.tv_currencyType)
    TextView tvCurrencyType;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_riskLevelCN)
    TextView tvRiskLevelCN;
    @Bind(R.id.tv_totalAmount)
    TextView tvTotalAmount;
    @Bind(R.id.tv_minInvestAmount)
    TextView tvMinInvestAmount;
    @Bind(R.id.tv_minInvestAmountAdd)
    TextView tvMinInvestAmountAdd;
    @Bind(R.id.rl_document)
    LinearLayout rlDocument;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.view_start_left)
    View viewStartLeft;
    @Bind(R.id.view_maturity_right)
    View viewMaturityRight;
    @Bind(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private ProductDetail mData;
//    private CollapsingToolbarLayoutState state;

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        showToastCenter(msg);
    }

    @Override
    public void returnProductDetail(ProductDetail data) {
        if (data != null) {
            mData = data;
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

            String rate = data.getTargetAnnualizedReturnRate() + "%";
            String term = data.getTerm() + "???";
            String amount = data.getMinInvestAmountNum() + data.getMinInvestAmountCN() + "?????????";
            tvTARRate.setText(setTxtLastStyle(rate, rate.length() - 1, rate.length()));//?????????????????????
//            mTvTARRateTop.setText(setRateTopStyle(rate));//???????????????????????????
            tvTerm.setText(setTxtLastStyle(term, term.length() - 1, term.length()));//????????????
            tvMinInvestAmountNum.setText(amount);
            //
            tvName.setText(data.getName());
            //???????????????
            tvTotalRaise.setText("??????" + data.getTotalRaise() + "???");
            double collectAmount = data.getCollectAmount();
//            double maxInvestAmount = data.getMaxInvestAmount();
            double totalRaise = Double.parseDouble(data.getTotalRaise().replace(",", ""));
            int progress = 0;
            if (collectAmount != 0) {
                progress = (int) ((collectAmount - totalRaise) / collectAmount * 100);
            }
            //??????
            seekBarBalance.setProgress(progress);

            //??????
            tvRiskLevel.setText(data.getRiskLevelCN());
            //????????????
            tvGuaranteeStatusCN.setText(data.getGuaranteeStatusCN());
            tvBankName.setText("???" + data.getCustodianCN() + "???");
            tvScode.setText("??????" + data.getScode());
            tvType.setText("???????????????" + data.getTypeCN());//????????????
            tvRiskLevelCN.setText("???????????????" + data.getRiskLevelCN());//????????????
            tvTotalAmount.setText("???????????????" + data.getCollectAmountCN() + "???");//????????????
            tvMinInvestAmount.setText("???????????????" + data.getMinInvestAmountLess() + "???");//????????????
            tvMinInvestAmountAdd.setText("???????????????" + data.getMinInvestAmountAddLess() + "???");//????????????
            tvCurrencyType.setText(data.getCurrencyTypeCN());//????????????

            //????????????
            setCycle(data.getStage());
            tvStartDate.setText(data.getCollectStarttimeWeb());//??????????????????
            tvEndDate.setText(data.getCollectEndtimeWeb());//??????????????????
            tvValueDate.setText(data.getValueDateWeb());//???????????????
            tvMaturityDate.setText(data.getMaturityDateWeb());//???????????????

            Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + data.getIconColorUrl()).into(ivBankIcon);

            nestedScrollView.setVisibility(View.VISIBLE);
//            btnBuy.setVisibility(View.VISIBLE);
            //??????????????????????????????????????????????????????????????????????????????tab?????????????????????????????????
            if (AppApplication.getInstance().isWebmarketSwitch()) {
                btnBuy.setVisibility(View.VISIBLE);
            } else {
                btnBuy.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_document://????????????
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstant.INTENT_PRODUCT_DETAIL, mData);
                startActivity(BankDetailAllActivity.class, bundle);
                break;
            case R.id.iv_back://??????
                finish();
                break;
            case R.id.btn_buy://??????
                toBuy();
                break;
            default:
                break;
        }
    }

    private void toBuy() {
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("????????????");
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        rlDocument.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String uuid = bundle.getString(AppConstant.PRODUCT_UUID);
            mPresenter.getProductDetail(uuid);
        }

        //???????????????????????????
        mRxManager.on(AppConstant.CERTIFICATION_PAY_SUCCESS, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstant.INTENT_PRODUCT_DETAIL, mData);
                startActivity(ProductBuyActivity.class, bundle);
            }
        });

        //??????????????????????????????????????????????????????????????????????????????tab?????????????????????????????????
//        if(AppApplication.getInstance().isWebmarketSwitch()){
//            btnBuy.setVisibility(View.VISIBLE);
//        }else {
//            btnBuy.setVisibility(View.GONE);
//        }
    }

    /**
     * ????????????
     */
    private void setCycle(String stage) {
        Drawable pointLight = getResources().getDrawable(R.drawable.bar_thumb_selected);
        Drawable pointDark = getResources().getDrawable(R.drawable.bar_thumb_normal);
        int colorDark = getResources().getColor(R.color.line_50_C2C2C2);
        int colorLight = getResources().getColor(R.color.color_0069E5);
        //????????????
        if (!UNSTART.equals(stage)) {
//            ivStart.setImageDrawable(pointLight);
            viewStartLeft.setBackgroundColor(colorLight);
            ivStart.setImageDrawable(pointLight);
            viewStartRight.setBackgroundColor(colorLight);
        } else {
            ivStart.setImageDrawable(pointDark);
            viewStartRight.setBackgroundColor(colorDark);
            viewStartLeft.setBackgroundColor(colorDark);
        }
        //????????????
        if (!UNSTART.equals(stage) && !COLLECT.equals(stage)) {
            ivEnd.setImageDrawable(pointLight);
            viewEndLeft.setBackgroundColor(colorLight);
            viewEndRight.setBackgroundColor(colorLight);
        } else {
            ivEnd.setImageDrawable(pointDark);
            viewEndLeft.setBackgroundColor(colorDark);
            viewEndRight.setBackgroundColor(colorDark);
        }
        //????????????
        if (PROFIT.equals(stage) || BALANCE.equals(stage) || FINISHED.equals(stage)) {
            ivValue.setImageDrawable(pointLight);
            viewValueLeft.setBackgroundColor(colorLight);
            viewValueRight.setBackgroundColor(colorLight);
        } else {
            ivValue.setImageDrawable(pointDark);
            viewValueLeft.setBackgroundColor(colorDark);
            viewValueRight.setBackgroundColor(colorDark);
        }
        //????????????
        if (BALANCE.equals(stage) || FINISHED.equals(stage)) {
            ivMaturity.setImageDrawable(pointLight);
            viewMaturityLeft.setBackgroundColor(colorLight);
            viewMaturityRight.setBackgroundColor(colorLight);
        } else {
            ivMaturity.setImageDrawable(pointDark);
            viewMaturityLeft.setBackgroundColor(colorDark);
            viewMaturityRight.setBackgroundColor(colorDark);
        }
    }

    /**
     * ???????????????start???end?????????????????????
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    private SpannableString setTxtLastStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(this, R.style.ProductRateTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }

    private SpannableString setTotalRaiseStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(this, R.style.ProductRateTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
