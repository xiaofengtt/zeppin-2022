package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.user.contract.CouponContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/12/19
 */

public class CouponModel implements CouponContract.Model {
    @Override
    public Observable<ResultData<Coupon>> getCouponList(String uuid, String status, String price, String token) {
        return Api.getDefault().getCouponList(uuid, status, price, token)
                .map(new Func1<ResultData<Coupon>, ResultData<Coupon>>() {
                    @Override
                    public ResultData<Coupon> call(ResultData<Coupon> couponResultData) {
                        return couponResultData;
                    }
                }).compose(RxSchedulers.<ResultData<Coupon>>io_main());
    }
}
