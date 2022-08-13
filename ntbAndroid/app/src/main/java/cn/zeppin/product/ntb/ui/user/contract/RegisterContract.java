package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import rx.Observable;

/**
 * 描述：注册
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface RegisterContract {
    interface Model extends BaseModel {
        Observable<ResultData2<User>> register(String token, String phone);

        Observable<ResultData2> sendCode(String phone, String codeType,String token);
    }

    interface View extends BaseView {
        void registerSuccess(String uuid);

        void sendCodeSuccess(String phone);

        void sendCodeFailed(String msg);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void register(String phone, String code, String pwd);

        public abstract void sendCode(String phone, String codeType);

    }
}
