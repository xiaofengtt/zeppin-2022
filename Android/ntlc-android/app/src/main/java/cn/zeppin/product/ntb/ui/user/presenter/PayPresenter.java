package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.PayContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：支付宝
 * 开发人: geng
 * 创建时间: 17/11/21
 */

public class PayPresenter extends PayContract.Presenter {
    @Override
    public void getPaymentList(final String uuid, final int pageNum, final int pageSize, final String sorts) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getPaymentList(uuid, pageNum, pageSize, sorts, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(new RxSubscriber<ResultData<PaymentList>>(mContext, false) {
                    @Override
                    protected void _onNext(ResultData<PaymentList> resultData) {
                        if (resultData.success()) {
                            mView.returnPaymentList(resultData.getData());
                        } else {
                            mView.showErrorTip(resultData.getMessage());
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

    @Override
    public void getAuthInfo4Ali(final String uuid) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getAuthInfo4Ali(uuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                    @Override
                    protected void _onNext(ResultData2<String> resultData2) {
                        if (resultData2.success()) {
                            mView.returnAuthInfo(resultData2.getData());
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

    @Override
    public void bindingUserInfoByAli(final String uuid, final String code) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.bindingUserInfoByAli(uuid, code, ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<User> resultData2) {
                                if (resultData2.success()) {
                                    mView.successBindingAli(resultData2.getData());
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

    @Override
    public void getAliQrCode(final String uuid) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getAliQrCode(uuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData<AlipayCode>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData<AlipayCode> resultData) {
                                if (resultData.success()) {
                                    mView.returnAliQrCode(resultData.getData());
                                } else {
                                    mView.showErrorTip(resultData.getMessage());
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                            }
                        }
                ));
            }

            @Override
            public void onError(String message) {
                mView.showErrorTip(message);
            }
        });
    }

    @Override
    public void rechargeByChanpay(final String uuid, final String price, final String bankcard, final String type, final String body, final String code, final String orderNum, final boolean isRegain) {
        if (!isRegain) {
            mView.showLoading("处理中...");
        }
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.rechargeByChanpay(uuid, EncryptUtil.getBase64(price), bankcard, type, body
                        , EncryptUtil.getBase64(code), orderNum, ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<String> resultData2) {
                                mView.stopLoading();
                                if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                                    if (resultData2.success()) {
                                        mView.SuccessByChanpay();
                                    } else {
                                        mView.showErrorTip(resultData2.getMessage());
                                    }
                                } else {//发送验证码
                                    if (resultData2.success()) {
                                        mView.sendCodeSuccess(resultData2.getData(), isRegain);
                                    } else {
                                        mView.sendCodeFailed(resultData2.getMessage());
                                    }
                                }

                            }

                            @Override
                            protected void _onError(String message) {
                                if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                                    mView.showErrorTip(message);
                                } else {
                                    mView.sendCodeFailed(message);
                                }
                                mView.stopLoading();
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                    mView.showErrorTip(message);
                } else {
                    mView.sendCodeFailed(message);
                }
                mView.stopLoading();
            }
        });

    }

    @Override
    public void getBindingCard(final String uuid) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getBindingCard(uuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData<MyBank>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData<MyBank> resultData) {
                                if (resultData.success()) {
                                    mView.returnMyBankList(resultData.getData());
                                } else {
                                    mView.showErrorTip(resultData.getMessage());
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

    @Override
    public void getUserInfo(final String uuid) {
        mView.showLoading("加载中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getUserInfo(uuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                    @Override
                    protected void _onNext(ResultData2<User> resultData2) {
                        if (resultData2.success()) {
                            mView.returnUserInfo(resultData2.getData());
                        } else {
                            mView.showErrorTip(resultData2.getMessage());
                        }
                        mView.stopLoading();
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
