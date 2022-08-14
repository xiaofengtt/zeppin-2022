package cn.zeppin.product.ntb.ui.finance.contract;

import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import rx.Observable;

/**
 * 描述：提现
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface WithdrawalContract {
    interface Model extends CommonContract.Model {
        Observable<ResultData2<String>> withdraw(
                String uuid,
                String price,
                String bankcard,
                String code,
                String token);

//        Observable<ResultData2<String>> sendCodeById(String uuid, String token);
    }

    interface View extends CommonContract.View {
        void withdrawSuccess();

        void sendCodeSuccess();

        void sendCodeFailed(String msg);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void withdraw(
                String uuid,
                String price,
                String bankcard,
                String code);

        public abstract void sendCodeById(String uuid);

        public abstract void getBindingCard(String uuid);
    }
}
