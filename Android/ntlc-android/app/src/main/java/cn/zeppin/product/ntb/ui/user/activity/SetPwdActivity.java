package cn.zeppin.product.ntb.ui.user.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.baseapp.AppManager;
import com.geng.library.commonutils.CollectionUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.ui.user.contract.SetPwdContract;
import cn.zeppin.product.ntb.ui.user.model.SetPwdModel;
import cn.zeppin.product.ntb.ui.user.presenter.SetPwdPresenter;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;

/**
 * 设置密码
 */
public class SetPwdActivity extends BaseActivity<SetPwdPresenter, SetPwdModel> implements SetPwdContract.View {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cet_pwd)
    ClearEditText cetPwd;
    @Bind(R.id.cet_confirmPwd)
    ClearEditText cetConfirmPwd;
    @Bind(R.id.btn_save)
    Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("设置密码");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSave();
            }
        });
    }

    /**
     * 保存
     */
    private void toSave() {
        if (TextUtils.isEmpty(getInputPwd())) {
            showToastCenter("请输入密码");
            return;
        }
        if (!CollectionUtils.checkPwd(getInputPwd())) {
            showToastCenter("请输入8-20位字母数字组合密码");
            return;
        }
        if (TextUtils.isEmpty(getInputConfirmPwd())) {
            showToastCenter("请输入确认密码");
            return;
        }
        if (!getInputPwd().equals(getInputConfirmPwd())) {
            showToastCenter("确认密码输入有误");
            return;
        }
        mPresenter.setPwd(AppApplication.getInstance().getUuid(), getInputPwd(),"");
    }

    private String getInputPwd() {
        return cetPwd.getText().toString();
    }

    private String getInputConfirmPwd() {
        return cetConfirmPwd.getText().toString();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog();
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
    public void setPwdSuccess() {
        final DialogBinding dialogBinding = new DialogBinding(SetPwdActivity.this, "设置成功！", R.drawable.set_pwd_success, "个人中心");
        dialogBinding.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBinding.dismiss();
                AppManager.getAppManager().finishOtherActivity(MainActivity.class);
            }
        });
        dialogBinding.setDeleteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBinding.dismiss();
                finish();
            }
        });
        dialogBinding.show();
    }
}
