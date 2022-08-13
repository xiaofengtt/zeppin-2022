package cn.zeppin.product.ntb.ui.finance.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.WithdrawalContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：提现
 * 开发人: geng
 * 创建时间: 17/10/24
 */

public class WithdrawalPresenter extends WithdrawalContract.Presenter {
    @Override
    public void withdraw(final String uuid, final String price, final String bankcard, final String code) {
        mView.showLoading("处理中...");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.withdraw(uuid, EncryptUtil.getBase64(price), bankcard,
                            EncryptUtil.getBase64(code), ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<String> resultData2) {
                                    mView.stopLoading();
                                    if (resultData2.success()) {
                                        mView.withdrawSuccess();
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
    public void sendCodeById(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.sendCodeById(uuid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<String> resultData2) {
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

    @Override
    public void getBindingCard(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getBindingCard(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData<MyBank>>(mContext, false) {
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
}
