package cn.zeppin.product.ntb.ui.main.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface GiftContract {
    interface Model extends BaseModel {
        Observable<ResultData2<Gift>> getNotActiveList(String uuid, String token);

        Observable<ResultData2<String>> receiveActivate(String uuid, String token, String coupons, boolean flagShare, String redPackets);
    }

    interface View extends BaseView {
        void returnNotActiveList(Gift gift);

        void receiveActivateSuccess(boolean flagShare);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getNotActiveList(String uuid);

        public abstract void receiveActivate(String uuid, String coupons, boolean flagShare, String redPackets);
    }
}
