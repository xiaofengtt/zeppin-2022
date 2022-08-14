package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.ResultData;
import rx.Observable;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface CouponContract {
    interface Model extends BaseModel {
        Observable<ResultData<Coupon>> getCouponList(String uuid, String status, String price, String token);
    }

    interface View extends BaseView {
        void returnCouponList(List<Coupon> list);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getCouponList(String uuid, String status, String price);
    }
}
