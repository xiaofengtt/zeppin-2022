package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdCheckContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：重置密码：验证
 * 开发人: geng
 * 创建时间: 17/9/26
 */

public class ResetPwdCheckPresenter extends ResetPwdCheckContract.Presenter {
    @Override
    public void resetCheck(final String phone, final String code) {
        mView.showLoading("处理中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.resetCheck(ApiConstants.getResetCheckToken(phone, code, serverTimestamp), EncryptUtil.getBase64(phone))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {
                                    mView.resetCheckSuccess();//验证成功
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

    @Override
    public void sendCode(final String phone, final String codeType) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.sendCode(EncryptUtil.getBase64(phone), EncryptUtil.getBase64(codeType), ApiConstants.sendCodeToken(phone, codeType, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {
                                    mView.sendCodeSuccess();
                                } else {
                                    mView.sendCodeFailed(resultData2.getMessage());
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.sendCodeFailed(message);
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                mView.sendCodeFailed(message);
            }
        });
    }
}
