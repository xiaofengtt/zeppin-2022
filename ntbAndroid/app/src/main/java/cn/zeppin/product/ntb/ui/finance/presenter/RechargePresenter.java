package cn.zeppin.product.ntb.ui.finance.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.RechargeContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：充值
 * 开发人: geng
 * 创建时间: 17/10/24
 */

public class RechargePresenter extends RechargeContract.Presenter {
    @Override
    public void echargeByChanpay(final String uuid, final String price, final String bankcard, final String type, final String body, final String code, final String orderNum) {
        if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
            mView.showLoading("处理中...");
        }

        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.rechargeByChanpay(uuid, EncryptUtil.getBase64(price), bankcard, type, body
                            , EncryptUtil.getBase64(code), orderNum, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<String> resultData2) {
                                    if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                                        mView.stopLoading();
                                        if (resultData2.success()) {
                                            mView.toSuccess();
                                        } else {
                                            mView.showErrorTip(resultData2.getMessage());
                                        }
                                    } else {//发送验证码
                                        if (resultData2.success()) {
                                            mView.sendCodeSuccess(resultData2.getData());
                                        } else {
                                            mView.sendCodeFailed(resultData2.getMessage());
                                        }
                                    }

                                }

                                @Override
                                protected void _onError(String message) {
                                    if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                                        mView.stopLoading();
                                        mView.showErrorTip(message);
                                    } else {
                                        mView.sendCodeFailed(message);
                                    }
                                }
                            }));
                } else {
                    if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                        mView.stopLoading();
                        mView.showErrorTip(resultData2.getMessage());
                    } else {//发送验证码
                        mView.sendCodeFailed(resultData2.getMessage());
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
                    mView.stopLoading();
                    mView.showErrorTip(message);
                } else {
                    mView.sendCodeFailed(message);
                }
            }
        }));
    }

    @Override
    public void getBindingCard(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getBindingCard(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(
                            new RxSubscriber<ResultData<MyBank>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData<MyBank> resultData) {
                                    if (resultData.success()) {
                                        mView.returnMyBankList(resultData.getData());
                                    } else {
                                        mView.returnFailed(resultData.getMessage());
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.returnFailed(message);
                                }
                            }));
                } else {
                    mView.returnFailed(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                mView.returnFailed(message);
            }
        }));
    }

    @Override
    public void getAuthInfo4Ali(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getAuthInfo4Ali(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<String> resultData2) {
                            mView.getAuthInfo(resultData2.getData());
                        }

                        @Override
                        protected void _onError(String message) {
                            mView.showErrorTip(message);
                        }
                    }));
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

}
