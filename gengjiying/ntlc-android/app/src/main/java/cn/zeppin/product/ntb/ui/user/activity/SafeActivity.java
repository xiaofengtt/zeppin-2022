package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.User;

import static cn.zeppin.product.ntb.R.id.rl_setPwd;

public class SafeActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_editPwd)
    RelativeLayout rlEditPwd;
    @Bind(rl_setPwd)
    RelativeLayout rlSetPwd;


    @Override
    public int getLayoutId() {
        return R.layout.activity_safe;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("账户安全");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlEditPwd.setOnClickListener(this);
        rlSetPwd.setOnClickListener(this);

        User user = AppApplication.getInstance().getUser();
        //是否设置过秘密
        if (user.isPwdFlag()) {
            rlEditPwd.setVisibility(View.VISIBLE);
            rlSetPwd.setVisibility(View.GONE);
        } else {
            rlEditPwd.setVisibility(View.GONE);
            rlSetPwd.setVisibility(View.VISIBLE);
        }
    }

    public void openCreateGestureActivity(View view) {
        startActivity(SetGestureActivity.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_editPwd://修改密码
                startActivity(EditPwdActivity.class);
                break;
            case rl_setPwd://设置密码
                startActivity(SetPwdActivity.class);
                break;
            default:
                break;
        }
    }
}
