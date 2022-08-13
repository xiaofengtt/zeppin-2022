package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/11/17
 */

public class CommonPresenter extends CommonContract.Presenter {
    @Override
    public void sendCodeById(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.sendCodeById(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2>(mContext, false) {
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

    @Override
    public void getBindingCard(final String uuid) {
        mView.showLoading(mContext.getResources().getString(R.string.loading));
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getBindingCard(uuid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData<MyBank>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData<MyBank> resultData) {
                                    mView.stopLoading();
                                    if (resultData.success()) {
                                        mView.returnMyBankList(resultData.getData());
                                    } else {
                                        mView.returnFailed(resultData.getMessage());
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.returnFailed(message);
                                    mView.stopLoading();
                                }

                            }));

                } else {
                    mView.returnFailed(resultData2.getMessage());
                    mView.stopLoading();
                }
            }

            @Override
            protected void _onError(String message) {
                mView.returnFailed(message);
                mView.stopLoading();
            }
        }));
    }

    @Override
    public void getUserInfo(final String uuid) {
        mView.showLoading("加载中...");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getInfo(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<User> resultData2) {
                            if (resultData2.success()) {
                                mView.getUserInfo(resultData2.getData());
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
}
