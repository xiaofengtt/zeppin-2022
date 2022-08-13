package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.AlipayAuthContract;

/**
 * 描述：支付宝授权
 * 开发人: geng
 * 创建时间: 17/11/21
 */

public class AlipayAuthPresenter extends AlipayAuthContract.Presenter {

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
    public void bindingUserInfoByAli(final String uuid, final String code) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.bindingUserInfoByAli(uuid, code, ApiConstants.getToken(uuid, resultData2.getData()))
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
