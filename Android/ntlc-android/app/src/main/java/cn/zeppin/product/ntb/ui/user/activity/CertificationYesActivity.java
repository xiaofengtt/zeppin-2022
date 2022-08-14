package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.NetWorkUtils;

import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import cn.zeppin.product.ntb.ui.user.presenter.CommonPresenter;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.ApiConstants.IdcardImgStatus.CHECKED;
import static cn.zeppin.product.ntb.app.ApiConstants.IdcardImgStatus.NOTUPLOAD;
import static cn.zeppin.product.ntb.app.ApiConstants.IdcardImgStatus.UNPASSED;

/**
 * 实名认证:已通过
 */
public class CertificationYesActivity extends BaseActivity<CommonPresenter, CommonModel> implements CommonContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_realName)
    TextView tvRealName;
    @Bind(R.id.tv_idCard)
    TextView tvIdCard;
    @Bind(R.id.tv_cerStatus)
    TextView tvCerStatus;
    @Bind(R.id.ll_cer)
    LinearLayout llCer;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    private User user = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_certification_yes;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("实名认证");
        ivBack.setOnClickListener(this);
        if (NetWorkUtils.isNetConnected(this)) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getUserInfo(uuid);
            }
        } else {
            showToastCenter(getResources().getString(R.string.no_net));
        }

        //监听信息变化
        mRxManager.on(AppConstant.REFRESH_USER, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_cer:
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstant.INTENT_USER, user);
                startActivity(CertificationActivity.class, bundle);
                break;
        }
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
    public void sendCodeSuccess() {

    }

    @Override
    public void sendCodeFailed(String message) {

    }

    @Override
    public void returnMyBankList(List<MyBank> list) {

    }

    @Override
    public void returnFailed(String error) {

    }

    @Override
    public void getUserInfo(User user) {
        this.user = user;
        tvRealName.setText(user.getRealname());
        tvIdCard.setText(user.getIdcard());
        tvCerStatus.setText(user.getIdcardImgStatusCN());
        if (CHECKED.equals(user.getIdcardImgStatus())) {//未通过时可点击重新认证
            tvCerStatus.setTextColor(getResources().getColor(R.color.color_74B230));
        } else {
            llCer.setOnClickListener(this);
        }
        //审核不通过和未上传状态可点击
        if (UNPASSED.equals(user.getIdcardImgStatus()) || NOTUPLOAD.equals(user.getIdcardImgStatus())) {
            llCer.setClickable(true);
        } else {
            llCer.setClickable(false);
        }
        AppApplication.getInstance().setUser(user);

        llContent.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(AppConstant.CERTIFICATION_PAY_SUCCESS, true);//实名认证成功通知
    }
}
