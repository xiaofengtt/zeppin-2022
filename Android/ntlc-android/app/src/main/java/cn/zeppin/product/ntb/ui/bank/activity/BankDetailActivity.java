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
            //是否可购买
            if (mData.isFlagBuy()) {
//                btnBuy.setEnabled(true);
                //募集完成的产品，详情页显示灰态按钮并显示“额度已满”
                if (UNINVEST.equals(mData.getStage())) {
                    btnBuy.setEnabled(false);
                    btnBuy.setText("额度已满");
                } else {
                    btnBuy.setEnabled(true);
                    btnBuy.setText("购买");
                }
            } else {
                btnBuy.setEnabled(false);
            }

            String rate = data.getTargetAnnualizedReturnRate() + "%";
            String term = data.getTerm() + "天";
            String amount = data.getMinInvestAmountNum() + data.getMinInvestAmountCN() + "元起购";
            tvTARRate.setText(setTxtLastStyle(rate, rate.length() - 1, rate.length()));//预期年化收益率
//            mTvTARRateTop.setText(setRateTopStyle(rate));//预期年化收益率顶部
            tvTerm.setText(setTxtLastStyle(term, term.length() - 1, term.length()));//产品期限
            tvMinInvestAmountNum.setText(amount);
            //
            tvName.setText(data.getName());
            //剩余募集额
            tvTotalRaise.setText("还剩" + data.getTotalRaise() + "元");
            double collectAmount = data.getCollectAmount();
//            double maxInvestAmount = data.getMaxInvestAmount();
            double totalRaise = Double.parseDouble(data.getTotalRaise().replace(",", ""));
            int progress = 0;
            if (collectAmount != 0) {
                progress = (int) ((collectAmount - totalRaise) / collectAmount * 100);
            }
            //进度
            seekBarBalance.setProgress(progress);

            //风险
            tvRiskLevel.setText(data.getRiskLevelCN());
            //保本保息
            tvGuaranteeStatusCN.setText(data.getGuaranteeStatusCN());
            tvBankName.setText("【" + data.getCustodianCN() + "】");
            tvScode.setText("编码" + data.getScode());
            tvType.setText("收益类型：" + data.getTypeCN());//收益类型
            tvRiskLevelCN.setText("风险等级：" + data.getRiskLevelCN());//风险等级
            tvTotalAmount.setText("计划募集：" + data.getCollectAmountCN() + "元");//计划募集
            tvMinInvestAmount.setText("起购金额：" + data.getMinInvestAmountLess() + "元");//起购金额
            tvMinInvestAmountAdd.setText("递增金额：" + data.getMinInvestAmountAddLess() + "元");//递增金额
            tvCurrencyType.setText(data.getCurrencyTypeCN());//理财币种

            //理财周期
            setCycle(data.getStage());
            tvStartDate.setText(data.getCollectStarttimeWeb());//申购起始日期
            tvEndDate.setText(data.getCollectEndtimeWeb());//申购结束日期
            tvValueDate.setText(data.getValueDateWeb());//产品起息日
            tvMaturityDate.setText(data.getMaturityDateWeb());//产品结束日

            Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + data.getIconColorUrl()).into(ivBankIcon);

            nestedScrollView.setVisibility(View.VISIBLE);
//            btnBuy.setVisibility(View.VISIBLE);
            //判断是否应用商场设置的开关是否打开，不打开时，底部的tab和产品的详情页不可购买
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
            case R.id.rl_document://产品说明
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstant.INTENT_PRODUCT_DETAIL, mData);
                startActivity(BankDetailAllActivity.class, bundle);
                break;
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_buy://购买
                toBuy();
                break;
            default:
                break;
        }
    }

    private void toBuy() {
        //登录判断
        if (TextUtils.isEmpty(AppApplication.getInstance().getUuid())) {
            startActivity(LoginActivity.class);
            return;
        }
        User user = AppApplication.getInstance().getUser();
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
        tvTitle.setText("产品详情");
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        rlDocument.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String uuid = bundle.getString(AppConstant.PRODUCT_UUID);
            mPresenter.getProductDetail(uuid);
        }

        //监听实名认证成功后
        mRxManager.on(AppConstant.CERTIFICATION_PAY_SUCCESS, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstant.INTENT_PRODUCT_DETAIL, mData);
                startActivity(ProductBuyActivity.class, bundle);
            }
        });

        //判断是否应用商场设置的开关是否打开，不打开时，底部的tab和产品的详情页不可购买
//        if(AppApplication.getInstance().isWebmarketSwitch()){
//            btnBuy.setVisibility(View.VISIBLE);
//        }else {
//            btnBuy.setVisibility(View.GONE);
//        }
    }

    /**
     * 理财周期
     */
    private void setCycle(String stage) {
        Drawable pointLight = getResources().getDrawable(R.drawable.bar_thumb_selected);
        Drawable pointDark = getResources().getDrawable(R.drawable.bar_thumb_normal);
        int colorDark = getResources().getColor(R.color.line_50_C2C2C2);
        int colorLight = getResources().getColor(R.color.color_0069E5);
        //申购起始
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
        //申购结束
        if (!UNSTART.equals(stage) && !COLLECT.equals(stage)) {
            ivEnd.setImageDrawable(pointLight);
            viewEndLeft.setBackgroundColor(colorLight);
            viewEndRight.setBackgroundColor(colorLight);
        } else {
            ivEnd.setImageDrawable(pointDark);
            viewEndLeft.setBackgroundColor(colorDark);
            viewEndRight.setBackgroundColor(colorDark);
        }
        //产品起息
        if (PROFIT.equals(stage) || BALANCE.equals(stage) || FINISHED.equals(stage)) {
            ivValue.setImageDrawable(pointLight);
            viewValueLeft.setBackgroundColor(colorLight);
            viewValueRight.setBackgroundColor(colorLight);
        } else {
            ivValue.setImageDrawable(pointDark);
            viewValueLeft.setBackgroundColor(colorDark);
            viewValueRight.setBackgroundColor(colorDark);
        }
        //产品到期
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
     * 设置字符串start到end之间字符的样式
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
