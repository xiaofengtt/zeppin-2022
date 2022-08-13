package cn.zeppin.product.ntb.ui.main.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.main.contract.GiftContract;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/12/19
 */

public class GiftPresenter extends GiftContract.Presenter {
    @Override
    public void getNotActiveList(final String uuid) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getNotActiveList(uuid, ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData2<Gift>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<Gift> data) {
                            if (data.success()) {
                                mView.returnNotActiveList(data.getData());
                            } else {
                                mView.showErrorTip(data.getMessage());
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
    public void receiveActivate(final String uuid, final String coupons, final boolean flagShare, final String redPackets) {
        mView.showLoading(mContext.getResources().getString(R.string.loading));
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.receiveActivate(uuid, ApiConstants.getToken(uuid, resultData2.getData()), coupons, flagShare, redPackets).subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2<String> data) {
                            if (data.success()) {
                                mView.receiveActivateSuccess(flagShare);
                            } else {
                                mView.showErrorTip(data.getMessage());
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
}
