package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.BaseActivity;

/**
 * 实名认证:未通过
 */
public class CertificationNoActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_error)
    TextView tvError;

    @Override
    public int getLayoutId() {
        return R.layout.activity_certification_no;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("实名认证");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String errorMsg = bundle.getString("errorMsg");
            if (!TextUtils.isEmpty(errorMsg)) {
                tvError.setText(errorMsg);
            }
        }
    }

    //重新认证
    public void reCertification(View view) {
        finish();
    }
}
