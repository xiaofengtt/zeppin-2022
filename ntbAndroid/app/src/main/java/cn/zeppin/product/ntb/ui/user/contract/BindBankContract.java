package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：绑定银行卡
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface BindBankContract {
    interface Model extends BaseModel {
        Observable<ResultData2<String>> bindingSendCode(
                String uuid,
                String bankcard,
                String phone,
                String bank,
                String cardholder,
                String token);

        Observable<ResultData2> bindingCard(
                String uuid,
                String bankcard,
                String phone,
                String code,
                String bank,
                String cardholder,
                String orderNum,
                String token);
    }

    interface View extends BaseView {
        void sendCodeSuccess(String orderNum,boolean isRegain);

        void sendCodeFailed(String error);

        void bindSuccess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void bindingSendCode(
                String uuid,
                String bankcard,
                String phone,
                String bank,
                String cardholder,
                boolean isRegain);

        public abstract void bindingCard(
                String uuid,
                String bankcard,
                String phone,
                String code,
                String bank,
                String cardholder,
                String orderNum);
    }
}
