package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
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
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.resetCheck(ApiConstants.getResetCheckToken(phone, code, resultData2.getData()), EncryptUtil.getBase64(phone))
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
                } else {
                    mView.stopLoading();
                    mView.showErrorTip(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void sendCode(final String phone, final String codeType) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.sendCode(EncryptUtil.getBase64(phone), EncryptUtil.getBase64(codeType), ApiConstants.sendCodeToken(phone, codeType, resultData2.getData())).subscribe(
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
}
