package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：找回密码
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface ResetPwdCheckContract {
    interface Model extends BaseModel {
        Observable<ResultData2> resetCheck(String token, String phone);

        Observable<ResultData2> sendCode(String phone, String codeType, String token);
    }

    interface View extends BaseView {
        void resetCheckSuccess();

        void sendCodeSuccess();

        void sendCodeFailed(String msg);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void resetCheck(String phone, String code);

        public abstract void sendCode(String phone, String codeType);

    }
}
