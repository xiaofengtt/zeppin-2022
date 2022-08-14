package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.BindBankContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：绑定银行卡
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class BindBankPresenter extends BindBankContract.Presenter {
    @Override
    public void bindingSendCode(final String uuid, final String bankcard, final String phone, final String bank, final String cardholder, final boolean isRegain) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.bindingSendCode(uuid, EncryptUtil.getBase64(bankcard),
                        EncryptUtil.getBase64(phone), bank, EncryptUtil.getBase64(cardholder),
                        ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<String> resultData2) {
                                if (resultData2.success()) {
                                    //短信发送成功
                                    mView.sendCodeSuccess(resultData2.getData(), isRegain);
                                } else {
                                    mView.sendCodeFailed(resultData2.getMessage());
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
    public void bindingCard(final String uuid, final String bankcard, final String phone, final String code, final String bank, final String cardholder, final String orderNum) {
        mView.showLoading("处理中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.bindingCard(uuid, EncryptUtil.getBase64(bankcard),
                        EncryptUtil.getBase64(phone), EncryptUtil.getBase64(code), bank,
                        EncryptUtil.getBase64(cardholder), orderNum, ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                mView.stopLoading();
                                if (resultData2.success()) {
                                    mView.bindSuccess();
                                } else {
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
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });

    }
}
