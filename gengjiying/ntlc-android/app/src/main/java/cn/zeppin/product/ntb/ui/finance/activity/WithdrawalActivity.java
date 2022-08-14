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
import android.text.style.TextAppearanceSpan;
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
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import java.math.BigDecimal;
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
import cn.zeppin.product.ntb.ui.finance.contract.WithdrawalContract;
import cn.zeppin.product.ntb.ui.finance.model.WithdrawalModel;
import cn.zeppin.product.ntb.ui.finance.presenter.WithdrawalPresenter;
import cn.zeppin.product.ntb.ui.user.activity.AddBankActivity;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.PriceInputFilter;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_AUTO_CLOSE;
import static cn.zeppin.product.ntb.app.AppConstant.SpfKey.WITHDRAWAL_BANK;
import static cn.zeppin.product.ntb.utils.MyUtil.setBackgroundAlpha;
import static com.geng.library.commonutils.CollectionUtils.closeKeyboard;
import static com.geng.library.commonutils.CollectionUtils.isHideInput;

/**
 * 提现
 */
public class WithdrawalActivity extends BaseActivity<WithdrawalPresenter, WithdrawalModel> implements WithdrawalContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.ll_selectBankCard)
    LinearLayout llSelectBankCard;
    @Bind(R.id.et_price)
    ClearEditText etPrice;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_withdrawal)
    Button btnWithdrawal;
    @Bind(R.id.ll_addBank)
    LinearLayout llAddBank;
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    private User user;
    private double accountBalance = 0.00;
    private List<MyBank> myBankList = new ArrayList<>();
    private List<MyBank> bankList = new ArrayList<>();
    //当前选的银行的uuid
    private MyBank currentMyBank;
    private DialogHelper sendSmsDialog;
    private CountDownTimer timer;
    private TextView tvGetCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(this);
        btnWithdrawal.setOnClickListener(this);
        llSelectBankCard.setOnClickListener(this);
        llAddBank.setOnClickListener(this);

        startProgressDialog();
        if (!NetWorkUtils.isNetConnected(this)) {
            showToastCenter(getResources().getString(R.string.no_net));
            stopProgressDialog();
        } else {
            SpannableString ss = new SpannableString("请输入提现金额");//定义hint的值
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置字体大小 true表示单位是sp
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            etPrice.setHint(new SpannedString(ss));

            user = AppApplication.getInstance().getUser();
            if (user != null) {
                accountBalance = Double.parseDouble(user.getAccountBalance());
                String accountBalance = "当前可提现金额为" + user.getAccountBalance() + "元";
                tvPrice.setText(setTxtStyle(accountBalance, 8, accountBalance.length() - 1));

            }

            if (accountBalance > 0 && accountBalance < 100) {
                etPrice.setText(user.getAccountBalance());
                etPrice.setCursorVisible(false);      //设置输入框中的光标不可见
                etPrice.setFocusable(false);           //无焦点
                etPrice.setFocusableInTouchMode(false);     //触摸时也得不到焦点
//                tvError.setText("当前余额不足最小提现金额100元，请全部提现");
//                tvError.setVisibility(View.VISIBLE);
//                btnWithdrawal.setEnabled(true);
                setErrorView("当前余额不足最小提现金额100元，请全部提现");
                btnWithdrawal.setText("提现" + CollectionUtils.numFormat4UnitDetailLess(accountBalance) + "元");
            }

            etPrice.addTextChangedListener(priceTextWatcher);

            //获取银行卡列表
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }

            //提现金额过滤
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
//            if (!TextUtils.isEmpty(getInputPrice())) {
//                if (inputPrice > accountBalance) {
//                    tvError.setVisibility(View.VISIBLE);
//                    btnWithdrawal.setEnabled(false);
//                    tvError.setText("输入的提现金额大于账户余额");
//                } else {
//                    tvError.setVisibility(View.GONE);
//                    btnWithdrawal.setEnabled(true);
//                }
//            } else {
//                tvError.setVisibility(View.GONE);
//                btnWithdrawal.setEnabled(false);
//            }
            if (!TextUtils.isEmpty(s.toString())) {
                double price = Double.parseDouble(s.toString());
                btnWithdrawal.setText("提现" + CollectionUtils.numFormat4UnitDetailLess(price) + "元");
            } else {
                btnWithdrawal.setText("提现");
            }
        }
    };

    private String getInputPrice() {
        return etPrice.getText().toString();
    }

    /**
     * 设置字符串start到end之间字符的样式
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    private SpannableString setTxtStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(this, R.style.BuyMoneyTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }


    /**
     * 选择银行弹框
     */
    private void openBankListDialog() {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_banklist, null);

        final PopupWindow pop = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        tvTitle.setText("选择提现银行");
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        bankList = new ArrayList<>();
        bankList.addAll(myBankList);
        String currentMyBankUUid = null;
        if (currentMyBank != null) {
            currentMyBankUUid = currentMyBank.getUuid();
        }
        SelectBankAdapter mAdapter = new SelectBankAdapter(bankList, currentMyBankUUid, false);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footView = inflater.inflate(R.layout.dialog_banklist_item_foot, null);
        mAdapter.setFooterView(footView);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentMyBank = myBankList.get(position);

                setCurrentBankView(currentMyBank);
                //记录当前选择的银行卡
                SpfUtil.putSerializable(WithdrawalActivity.this, WITHDRAWAL_BANK, currentMyBank);
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
        setBackgroundAlpha(this, 0.6f);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBackgroundAlpha(WithdrawalActivity.this, 1f);
            }
        });
    }

    private void popDismiss(PopupWindow pop) {
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }


    private void openSendsmsDialog(String title) {
        final String uuid = AppApplication.getInstance().getUuid();

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
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                        String inputPrice = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//以分为单位
                        mPresenter.withdraw(uuid, inputPrice, currentMyBank.getUuid(), etCode.getText().toString());
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
                mPresenter.sendCodeById(uuid);
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
    }


    public void returnMyBankList(List<MyBank> list) {
        myBankList.clear();
        if (list != null && list.size() > 0) {
            isAddBank(false);
            myBankList.addAll(list);
            //获取之前选择的 MyBank saveBank = (MyBank) SpfUtil.getSerializable(this, WITHDRAWAL_BANK);
//            if (saveBank != null) {
//                for (int i = 0; i < myBankList.size(); i++) {
//                    if (myBankList.get(i).getUuid().equals(saveBank.getUuid())) {
//
//                    }
//                }
//                currentMyBank = (MyBank) saveBank;
//            } else {
//                currentMyBank = myBankList.get(0);
//            }银行卡
//
            currentMyBank = initCurrentMyBank(myBankList);
            setCurrentBankView(currentMyBank);
        } else {
            SpfUtil.remove(this, WITHDRAWAL_BANK);
            isAddBank(true);
        }
        llContent.setVisibility(View.VISIBLE);
        stopProgressDialog();
    }

    /**
     * 默认选中一张提现银行卡
     *
     * @param list
     * @return
     */
    private MyBank initCurrentMyBank(List<MyBank> list) {
        //获取之前选择的银行卡,
        MyBank saveBank = (MyBank) SpfUtil.getSerializable(this, WITHDRAWAL_BANK);
        if (saveBank != null) {
            //检测下当前绑卡列表里是否有该卡
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUuid().equals(saveBank.getUuid())) {
                    return saveBank;
                }
            }
        }
        //走到这里表示列表没有该卡，已在别的设备解绑，这时清除本地保存的数据
        SpfUtil.remove(this, WITHDRAWAL_BANK);
        return list.get(0);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_withdrawal://提现
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                    toWithdrawal();
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

    /**
     * 提现 增加最小提现金额 100
     * 判断 余额小于100的只能提现全部余额
     */
    private void toWithdrawal() {
        if (!NetWorkUtils.isNetConnected(this)) {
            setErrorView(getResources().getString(R.string.no_net));
            return;
        }
        if (currentMyBank == null) {
            setErrorView("请先选择提现的银行卡");
            return;
        }

        if (TextUtils.isEmpty(getInputPrice())) {
            setErrorView("请输入提现金额");
            return;
        }
        double inputPrice = Double.parseDouble(getInputPrice());
        if (inputPrice <= 0) {
            setErrorView("请输入有效的提现金额");
            return;
        }

        if (inputPrice > accountBalance) {
            setErrorView("输入的提现金额大于账户余额");
            return;
        }

//        if (accountBalance > 0 && accountBalance < 100) {
//            setErrorView("当前余额不足最小提现金额100元，请全部提现");
//        }

        if (accountBalance >= 100 && inputPrice < 100) {//账户余额大于等于100且提现金额小于100
            setErrorView("最小提现金额为100元");
            return;
        }

        tvError.setVisibility(View.GONE);
        //登录的账号
        openSendsmsDialog("发送至" + AppApplication.getInstance().getUser().getPhone() + "的手机");
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
    public void withdrawSuccess() {
        sendSmsDialog.dismiss();
        if (timer != null) {
            timer.cancel();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.INTENT_WITHDRAWAL_PRICE, getInputPrice());
        bundle.putString(AppConstant.INTENT_BANK, currentMyBank.getName() + " 尾号" + currentMyBank.getBankcard());
        startActivity(WithdrawalApplyActivity.class, bundle);
        finish();
    }

    @Override
    public void sendCodeSuccess() {
        showToastCenter("验证码已发送成功");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.RequestCode.REQUEST_CODE_BANKLIST) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }

    private void setCurrentBankView(MyBank myBank) {
        tvBankName.setText(myBank.getShortName() + "储蓄卡（" + myBank.getBankcard() + "）");
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBank.getIconColor()).into(ivBankIcon);

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

    private void openAddBankActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INTENT_AUTO_CLOSE, true);
        startActivityForResult(AddBankActivity.class, bundle, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //屏幕任意处关闭软键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                closeKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setErrorView(String error) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
        showToastCenter(error);
    }
}
