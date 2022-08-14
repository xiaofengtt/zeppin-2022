package cn.zeppin.product.ntb.ui.finance.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.commonutils.NetWorkUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.bank.adapter.SelectBankAdapter;
import cn.zeppin.product.ntb.ui.finance.contract.RechargeContract;
import cn.zeppin.product.ntb.ui.finance.model.RechargeModel;
import cn.zeppin.product.ntb.ui.finance.presenter.RechargePresenter;
import cn.zeppin.product.ntb.ui.user.activity.AddBankActivity;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.PriceInputFilter;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_AUTO_CLOSE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BUY_PRICE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_RECHARGE_PRICE;
import static cn.zeppin.product.ntb.app.AppConstant.SpfKey.RECHARGE_BANK;
import static cn.zeppin.product.ntb.utils.MyUtil.setBackgroundAlpha;
import static com.geng.library.commonutils.CollectionUtils.closeKeyboard;
import static com.geng.library.commonutils.CollectionUtils.isHideInput;
import static com.geng.library.commonutils.CollectionUtils.numFormat4UnitString;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity<RechargePresenter, RechargeModel> implements RechargeContract.View, View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_payBankIcon)
    ImageView ivPayBankIcon;
    @Bind(R.id.tv_payBankName)
    TextView tvPayBankName;
    @Bind(R.id.ll_selectBankCard)
    LinearLayout llSelectBankCard;
    @Bind(R.id.et_price)
    ClearEditText etPrice;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.ll_addBank)
    LinearLayout llAddBank;
    @Bind(R.id.tv_singleLimit)
    TextView tvSingleLimit;
    @Bind(R.id.tv_dailyLimit)
    TextView tvDailyLimit;
    @Bind(R.id.ll_bankLimit)
    LinearLayout llBankLimit;
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    private List<MyBank> myBankList = new ArrayList<>();
    private List<MyBank> bankList = new ArrayList<>();
    //当前选的银行
    private MyBank currentMyBank;
    private DialogHelper sendSmsDialog;
    private CountDownTimer timer;
    private TextView tvGetCode;
    private String mOrderNum;//订单编号
    private String uuid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        llSelectBankCard.setOnClickListener(this);
        llAddBank.setOnClickListener(this);

        startProgressDialog();
        if (!NetWorkUtils.isNetConnected(this)) {
            showToastCenter(getResources().getString(R.string.no_net));
        } else {
            SpannableString ss = new SpannableString("请输入充值金额");//定义hint的值
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置字体大小 true表示单位是sp
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            etPrice.setHint(new SpannedString(ss));
            etPrice.addTextChangedListener(priceTextWatcher);

            uuid = AppApplication.getInstance().getUuid();
            mPresenter.getBindingCard(uuid);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                //购买的金额
                double buyPrice = Double.parseDouble(bundle.getString(INTENT_BUY_PRICE));
//                double accountBalance = Double.parseDouble(AppApplication.getInstance().getUser().getAccountBalance());
//                double rechargePrice = buyPrice - accountBalance;
                if (buyPrice > 1) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    etPrice.setText(df.format(buyPrice) + "");
                } else {
                    etPrice.setText(buyPrice + "");
                }
            }
            //充值金额过滤
            InputFilter[] filters = {new PriceInputFilter()};
            etPrice.setFilters(filters);
        }
    }

    /**
     * 金额输入监听
     */
    private TextWatcher priceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString())) {
                double price = Double.parseDouble(s.toString());
                if (getSingleLimit() != 0 && price > getSingleLimit()) {//大于银行单笔限额
                    tvError.setVisibility(View.VISIBLE);
                    btnPay.setEnabled(false);
                    tvError.setText("当前充值金额超出限额，请分笔充值");
                } else {
                    tvError.setVisibility(View.GONE);
                    btnPay.setEnabled(true);
                }
            } else {
                tvError.setVisibility(View.GONE);
                btnPay.setEnabled(false);
            }
        }
    };

    private String getInputPrice() {
        return etPrice.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_pay://支付
                if (!NetWorkUtils.isNetConnected(this)) {
                    showToastCenter(getResources().getString(R.string.no_net));
                    return;
                }
                if (currentMyBank == null) {
                    showToastCenter("请先选择充值的银行卡");
                    return;
                }
                double price = Double.parseDouble(getInputPrice());
                if (price < 4) {
                    showToastCenter("充值金额最低4元");
                } else {
                    //银行预留手机号
                    openSendsmsDialog("发送至" + currentMyBank.getPhone() + "的手机");
                }
                break;
            case R.id.ll_selectBankCard://选择银行
                User user = AppApplication.getInstance().getUser();
                if (user != null && user.getBankcardCount() > 0) {
                    //已绑定
                    openBankListDialog();
                } else {
                    //还未绑定银行卡
                    final DialogBinding dialogBinding = new DialogBinding(this, "您还未绑定银行卡！", R.drawable.unbind_bankcard, "马上绑卡");
                    dialogBinding.setConfirmClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBinding.dismiss();
                            openAddBankActivity();
                        }
                    });
                    dialogBinding.setDeleteClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBinding.dismiss();
                        }
                    });
                    dialogBinding.show();
                }
                break;
            case R.id.ll_addBank://添加银行
                openAddBankActivity();
                break;
            default:
                break;
        }
    }

    private void openAddBankActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INTENT_AUTO_CLOSE, true);
        startActivityForResult(AddBankActivity.class, bundle, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showToastCenter(msg);
    }

    @Override
    public void toSuccess() {
        //充值成功
        sendSmsDialog.dismiss();
        if (timer != null) {
            timer.cancel();
        }
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_RECHARGE_PRICE, getInputPrice());
        startActivity(RechargeSuccessActivity.class, bundle);
        finish();
    }

    @Override
    public void sendCodeSuccess(String orderNum) {
        //充值验证码发送成功
        mOrderNum = orderNum;
        showToastCenter("验证码发送成功");
    }

    @Override
    public void sendCodeSuccess() {

    }

    @Override
    public void sendCodeFailed(String error) {
        showToastCenter(error);
        if (timer != null) {
            timer.cancel();
            tvGetCode.setText("获取验证码");
            tvGetCode.setEnabled(true);
        }
    }

    @Override
    public void getAuthInfo(String authInfo) {

    }


    /**
     * 选择银行弹框
     */
    private void openBankListDialog() {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_banklist, null);

        final PopupWindow pop = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        tvTitle.setText("选择充值银行");
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        bankList = new ArrayList<>();
        bankList.addAll(myBankList);

        String currentMyBankUUid = null;
        if (currentMyBank != null) {
            currentMyBankUUid = currentMyBank.getUuid();
        }
        SelectBankAdapter mAdapter = new SelectBankAdapter(myBankList, currentMyBankUUid,true);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footView = inflater.inflate(R.layout.dialog_banklist_item_foot, null);
        mAdapter.setFooterView(footView);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentMyBank = myBankList.get(position);
                setCurrentBankView(currentMyBank);
                checkSingleLimit();

                //记录当前选择的银行卡
                SpfUtil.putSerializable(RechargeActivity.this, RECHARGE_BANK, currentMyBank);
                popDismiss(pop);
            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加银行卡
                popDismiss(pop);
                openAddBankActivity();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//关闭
                popDismiss(pop);
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popDismiss(pop);
            }
        });
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popDismiss(pop);
                }
                return false;
            }
        });


        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.DialogBankList);
        pop.showAtLocation(contentView, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(this, 0.5f);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBackgroundAlpha(RechargeActivity.this, 1f);
            }
        });
    }

    private void popDismiss(PopupWindow pop) {
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }

    private void checkSingleLimit() {
        if (!TextUtils.isEmpty(getInputPrice())) {
            double price = Double.parseDouble(getInputPrice());
            if (getSingleLimit() != 0 && price > getSingleLimit()) {//大于银行单笔限额
                tvError.setVisibility(View.VISIBLE);
                btnPay.setEnabled(false);
                tvError.setText("当前充值金额超出限额，请分笔充值");
            }
        }
    }


    private void openSendsmsDialog(String title) {
        final String uuid = AppApplication.getInstance().getUuid();
        final String price = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//已分为单位

        sendSmsDialog = new DialogHelper(this, R.style.dialog_prompt);
        sendSmsDialog.setContentView(R.layout.dialog_sendsms);
        // 设置标题
        ((TextView) sendSmsDialog.findViewById(R.id.tv_title)).setText(title);
        final ClearEditText etCode = (ClearEditText) sendSmsDialog.findViewById(R.id.et_code);
        tvGetCode = (TextView) sendSmsDialog.findViewById(R.id.tv_getCode);

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText("重新获取");
            }
        };
        // 设置确定按钮
        Button confirm = (Button) sendSmsDialog.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etCode.getText()) && etCode.getText().length() == 6) {
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm) || mOrderNum == null) {
                        mPresenter.echargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.CHECK, "", etCode.getText().toString(), mOrderNum);
                    }
                } else {
                    showToastCenter("请输入正确的验证码");
                }
            }
        });
        //设置取消按钮
        Button cancel = (Button) sendSmsDialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsDialog.dismiss();
                timer.cancel();
            }
        });
        //获取短信验证码
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGetCode.setEnabled(false);
                timer.start();
                mPresenter.echargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "");
            }
        });
        sendSmsDialog.setCancelable(false);

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = sendSmsDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        sendSmsDialog.show();
        //自动获取验证码
        tvGetCode.setEnabled(false);
        timer.start();
        mPresenter.echargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "");
    }


    public void returnMyBankList(List<MyBank> list) {
        myBankList.clear();
        if (list != null && list.size() > 0) {
            myBankList.addAll(list);
            isAddBank(false);
            //获取之前选择的银行卡
            Object bankObject = SpfUtil.getSerializable(this, RECHARGE_BANK);
            if (bankObject != null) {
                currentMyBank = (MyBank) bankObject;
            } else {
                currentMyBank = myBankList.get(0);
            }
            setCurrentBankView(currentMyBank);
        } else {
            SpfUtil.remove(this, RECHARGE_BANK);
            isAddBank(true);
        }
        llContent.setVisibility(View.VISIBLE);
        stopProgressDialog();
    }

    @Override
    public void returnFailed(String error) {
        showToastCenter(error);
        llContent.setVisibility(View.GONE);
        stopProgressDialog();
    }

    @Override
    public void getUserInfo(User user) {

    }

    private void setCurrentBankView(MyBank myBank) {
        tvPayBankName.setText(myBank.getShortName() + "(" + myBank.getBankcard() + ")");
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBank.getIconColor()).into(ivPayBankIcon);

        tvSingleLimit.setText(numFormat4UnitString(myBank.getSingleLimit()));
        tvDailyLimit.setText(numFormat4UnitString(myBank.getDailyLimit()));
        llBankLimit.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.RequestCode.REQUEST_CODE_BANKLIST) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }

    /**
     * 获取当前选择的银行，单笔限额
     *
     * @return
     */
    private double getSingleLimit() {
        if (currentMyBank != null) {
            return currentMyBank.getSingleLimit();
        }
        return 0;
    }

    /**
     * 是否需要添加银行
     */
    private void isAddBank(boolean isAdd) {
        if (isAdd) {
            llAddBank.setVisibility(View.VISIBLE);
            llSelectBankCard.setVisibility(View.GONE);
        } else {
            llAddBank.setVisibility(View.GONE);
            llSelectBankCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                closeKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
