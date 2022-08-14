package cn.zeppin.product.ntb.ui.finance.contract;

import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import rx.Observable;

/**
 * 描述：充值
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface RechargeContract {
    interface Model extends CommonContract.Model {
        Observable<ResultData2<String>> rechargeByChanpay(
                String uuid,
                String price,
                String bankcard,
                String type,
                String body,
                String code,
                String orderNum,
                String token);

        Observable<ResultData2<String>> getAuthInfo4Ali(
                String uuid,
                String token);
    }

    interface View extends CommonContract.View {
        void toSuccess();

        void sendCodeSuccess(String orderNum);

        void sendCodeFailed(String error);

        void getAuthInfo(String authInfo);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void echargeByChanpay(
                String uuid,
                String price,
                String bankcard,
                String type,
                String body,
                String code,
                String orderNum);

        public abstract void getBindingCard(String uuid);

        public abstract void getAuthInfo4Ali(String uuid);
    }
}
