package cn.zeppin.product.ntb.ui.finance.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;

public class WithdrawalApplyActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_des)
    TextView tvDes;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal_apply;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("提现申请");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String price = bundle.getString(AppConstant.INTENT_WITHDRAWAL_PRICE);
            String bankName = bundle.getString(AppConstant.INTENT_BANK);
            double er = Double.parseDouble(price);
            DecimalFormat df = new DecimalFormat("#.00");

            tvBankName.setText(bankName);
            tvPrice.setText(df.format(er));

            //提示信息：若用户提现金额小于5万元则提示用户“预计2小时内到账”，
            // 如果高于5万元则判断当前时间，提示“明日10:00前到账，如遇节假日顺延”。
            if (er <= 50000) {
                tvDes.setText("预计2小时内到账");
            } else {
                tvDes.setText("明日10:00前到账，如遇节假日顺延");
            }
        }
    }
}
