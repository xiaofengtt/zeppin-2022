package cn.zeppin.product.ntb.ui.bank.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.TimeUtil;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;

import static cn.zeppin.product.ntb.app.AppConstant.TAB_INDEX;

/**
 * 支付成功页
 */
public class PaySuccessActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;


    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("结果详情");
        tvTime.setText(TimeUtil.getData(TimeUtil.sdf2));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //我的持仓
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppConstant.TO_MAIN);
                intent.putExtra(TAB_INDEX, AppConstant.FragmentIndex.FINANCE);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(AppConstant.PRODUCT_BUY_SUCCESS, true);//刷新用户信息
    }
}
