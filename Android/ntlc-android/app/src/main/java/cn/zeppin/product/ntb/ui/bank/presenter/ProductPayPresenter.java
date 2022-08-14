package cn.zeppin.product.ntb.ui.bank.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.bank.contract.ProductPayContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：购买理财
 * 开发人: geng
 * 创建时间: 17/10/19
 */

public class ProductPayPresenter extends ProductPayContract.Presenter {
    @Override
    public void sendCodeById(final String uuid, final String price, final String product, final boolean isRegain) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.sendCodeById(uuid, ApiConstants.getToken(uuid, resultData2.getData()), EncryptUtil.getBase64(price), product).subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2 resultData2) {
                            if (resultData2.success()) {
                                mView.sendCodeSuccess(isRegain);
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
                } else {
                    mView.showErrorTip(resultData2.getMessage());
                    mView.stopLoading();
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
                mView.stopLoading();
            }
        }));
    }

    @Override
    public void productPay(final String uuid, final String price, final String product, final String type, final String code, final String coupon) {
        mView.showLoading("处理中...");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.productPay(uuid, EncryptUtil.getBase64(price), product, type, EncryptUtil.getBase64(code), coupon, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<String> resultData2) {
                                    mView.stopLoading();
                                    if (resultData2.success()) {
                                        //支付成功
                                        mView.paySuccess();
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
    public void getUserInfo(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                mRxManage.add(mModel.getInfo(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                    @Override
                    protected void _onNext(ResultData2<User> resultData2) {
                        if (resultData2.success()) {
                            mView.getUserInfo(resultData2.getData());
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
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

}
