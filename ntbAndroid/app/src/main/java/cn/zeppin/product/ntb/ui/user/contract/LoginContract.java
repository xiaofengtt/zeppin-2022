package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import rx.Observable;

/**
 * 描述：登录
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface LoginContract {
    interface Model extends BaseModel {
        Observable<ResultData2<User>> login(String token);
        Observable<ResultData2<User>> loginBycode(String token);
    }

    interface View extends BaseView {
        void loginSuccess(User user);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void login(String phone, String pwd);
        public abstract void loginBycode(String phone, String code);

    }
}
