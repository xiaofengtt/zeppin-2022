package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：解绑银行卡
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface UnBindBankContract {
    interface Model extends BaseModel {
        Observable<ResultData2> unbindSendCode(
                String uuid,
                String bankcard,
                String phone,
                String token);

        Observable<ResultData2> unbindBankcard(
                String uuid,
                String phone,
                String code,
                String bankcard,
                String token);
    }

    interface View extends BaseView {
        void sendCodeSuccess();
        void sendCodeFailed(String error);

        void unbindSuccess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void unbindSendCode(
                String uuid,
                String bankcard,
                String phone);

        public abstract void unbindBankcard(
                String uuid,
                String phone,
                String code,
                String bankcard);
    }
}
