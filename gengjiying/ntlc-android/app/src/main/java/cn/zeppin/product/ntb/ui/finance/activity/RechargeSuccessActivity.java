package cn.zeppin.product.ntb.ui.finance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.baserx.RxBus;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;

import static cn.zeppin.product.ntb.app.AppConstant.TAB_INDEX;

/**
 * 充值成功
 */
public class RechargeSuccessActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_success;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("充值成功");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去投资
                Intent intent = new Intent(AppConstant.TO_MAIN);
                intent.putExtra(TAB_INDEX, AppConstant.FragmentIndex.BANK);
                sendBroadcast(intent);
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String price = bundle.getString(AppConstant.INTENT_RECHARGE_PRICE);
            tvContent.setText("充值成功，充值金额" + price + "元");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }
}
