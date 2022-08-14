package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import rx.Observable;

/**
 * 描述：支付
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface PayContract {
    public interface Model extends BaseModel {
        Observable<ResultData<PaymentList>> getPaymentList(
                String uuid,
                int pageNum,
                int pageSize,
                String sorts,
                String token);

        //支付宝支付-------------------
        Observable<ResultData2<String>> getAuthInfo4Ali(
                String uuid,
                String token);

        Observable<ResultData2<User>> bindingUserInfoByAli(
                String uuid,
                String code,
                String token);

        Observable<ResultData<AlipayCode>> getAliQrCode(
                String uuid,
                String token);
        //---------------------------

        //畅捷支付-------包含发送验证，通过type区分---check充值，send发送验证码-----------
        Observable<ResultData2<String>> rechargeByChanpay(
                String uuid,
                String price,
                String bankcard,
                String type,
                String body,
                String code,
                String orderNum,
                String token);

        Observable<ResultData<MyBank>> getBindingCard(String uuid, String token);
        //----------------------------

        //获取用户信息
        Observable<ResultData2<User>> getUserInfo(String uuid, String token);
    }

    interface View extends BaseView {
        void returnPaymentList(List<PaymentList> list);
        //支付宝----------------

        void returnAuthInfo(String authInfo);

        void returnAliQrCode(List<AlipayCode> list);

        void successBindingAli(User user);

        //畅捷-----------------
        void sendCodeSuccess(String orderNum,boolean isRegain);

        void sendCodeFailed(String error);

        void SuccessByChanpay();

        //已绑定的银行卡
        void returnMyBankList(List<MyBank> list);

        //获取用户信息成功
        void returnUserInfo(User user);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getPaymentList(
                String uuid,
                int pageNum,
                int pageSize,
                String sorts);

        public abstract void getAuthInfo4Ali(String uuid);

        public abstract void bindingUserInfoByAli(String uuid, String code);

        public abstract void getAliQrCode(String uuid);

        public abstract void rechargeByChanpay(
                String uuid,
                String price,
                String bankcard,
                String type,
                String body,
                String code,
                String orderNum,
                boolean isRegain);

        public abstract void getBindingCard(String uuid);

        public abstract void getUserInfo(String uuid);
    }
}
