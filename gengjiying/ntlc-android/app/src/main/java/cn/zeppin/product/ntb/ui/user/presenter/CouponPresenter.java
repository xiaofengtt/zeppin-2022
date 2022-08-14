package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.user.contract.CouponContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/12/19
 */

public class CouponPresenter extends CouponContract.Presenter {
    @Override
    public void getCouponList(final String uuid, final String status, final String price) {
        mView.showLoading(mContext.getResources().getString(R.string.loading));
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getCouponList(uuid, status, EncryptUtil.getBase64(price), ApiConstants.getToken(uuid, serverTimestamp)).subscribe(new RxSubscriber<ResultData<Coupon>>(mContext, false) {
                    @Override
                    protected void _onNext(ResultData<Coupon> couponResultData) {
                        if (couponResultData.success()) {
                            mView.returnCouponList(couponResultData.getData());
                        } else {
                            mView.showErrorTip(couponResultData.getMessage());
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
}
