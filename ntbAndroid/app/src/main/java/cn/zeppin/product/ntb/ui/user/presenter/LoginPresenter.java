package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.LoginContract;

/**
 * 描述：登录
 * 开发人: geng
 * 创建时间: 17/9/26
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void login(final String phone, final String pwd) {
        mView.showLoading("登录中...");

        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.login(ApiConstants.getLoginToken(phone, pwd, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<User> resultData2) {
                                    mView.stopLoading();
                                    if (resultData2.success()) {
                                        mView.loginSuccess(resultData2.getData());
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
    public void loginBycode(final String phone, final String code) {
        mView.showLoading("登录中...");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.loginBycode(ApiConstants.getLoginBycodeToken(phone, code, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<User>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<User> resultData2) {
                            mView.stopLoading();
                            if (resultData2.success()) {
                                mView.loginSuccess(resultData2.getData());
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
}
