package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.UnBindBankContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：解绑银行卡
 * 开发人: geng
 * 创建时间: 17/10/11
 */

public class UnbindBankPresenter extends UnBindBankContract.Presenter {
    @Override
    public void unbindSendCode(final String uuid, final String bankcard, final String phone) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.unbindSendCode(uuid, bankcard, EncryptUtil.getBase64(phone), ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
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

    @Override
    public void unbindBankcard(final String uuid, final String phone, final String code, final String bankcard) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.unbindBankcard(uuid, EncryptUtil.getBase64(phone),
                        EncryptUtil.getBase64(code), bankcard, ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, true) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {
                                    mView.unbindSuccess();
                                } else {
                                    mView.showErrorTip(resultData2.getMessage());
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                mView.showErrorTip(message);
            }
        });
    }
}
