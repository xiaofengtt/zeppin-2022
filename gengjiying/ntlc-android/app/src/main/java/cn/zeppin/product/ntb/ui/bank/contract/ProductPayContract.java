package cn.zeppin.product.ntb.ui.bank.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import rx.Observable;

/**
 * Created by geng on 17/9/6.
 * class: 购买理财
 */

public interface ProductPayContract {
    interface Model extends CommonContract.Model {
//        Observable<ResultData<MyBank>> getBindingCard(String uuid);
//
//        /**
//         * 银行支付&发送短信
//         *
//         * @param uuid
//         * @param bankcard
//         * @param price
//         * @param product
//         * @param type
//         * @param code
//         * @param orderNum
//         * @return
//         */
//        Observable<ResultData2<String>> productPayByChanpay(
//                String uuid,
//                String bankcard,
//                String price,
//                String product,
//                String type,
//                String code,
//                String orderNum);

        /**
         * 余额支付发送短信
         *
         * @return
         */
//        Observable<ResultData2<String>> sendCodeById(String uuid, String token);

        /**
         * 余额支付发送短信
         *
         * @return
         */
        Observable<ResultData2<String>> sendCodeById(String uuid, String token, String price, String produce);

        /**
         * 余额支付
         *
         * @param uuid
         * @param price
         * @param product
         * @param code
         * @return
         */
        Observable<ResultData2<String>> productPay(
                String uuid,
                String price,
                String product,
                String type,
                String code,
                String coupon,
                String token);

        Observable<ResultData2<User>> getInfo(String uuid, String token);
    }

    interface View extends BaseView {
//        void returnMyBankList(List<MyBank> list);

        void paySuccess();

        void sendCodeSuccess(boolean isRegain);

        void sendCodeFailed(String message);

        void getUserInfo(User user);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //        public abstract void getBindingCard(String uuid);
//
//        public abstract void productPayByChanpay(
//                String uuid,
//                String bankcard,
//                String price,
//                String product,
//                String type,
//                String code,
//                String orderNum);
//        public abstract void sendCodeById(String uuid);


        public abstract void sendCodeById(String uuid, String price, String product,boolean isRegain);

        public abstract void productPay(
                String uuid,
                String price,
                String product,
                String type,
                String code,
                String coupon);

        public abstract void getUserInfo(String uuid);
    }
}
