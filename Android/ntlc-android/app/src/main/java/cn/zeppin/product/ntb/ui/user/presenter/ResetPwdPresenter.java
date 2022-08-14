package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/9/28
 */

public class ResetPwdPresenter extends ResetPwdContract.Presenter {
    @Override
    public void resetPwd(final String pwd, final String phone, final String code) {
        mView.showLoading("处理中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.resetPwd(ApiConstants.getResetPwdToken(pwd, phone, code, serverTimestamp), EncryptUtil.getBase64(phone))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {
                                    mView.resetSuccess();
                                } else {
                                    mView.showErrorTip(resultData2.getMessage());
                                }
                                mView.stopLoading();
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                                mView.stopLoading();
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });
    }
}
