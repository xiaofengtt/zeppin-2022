package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import rx.Observable;

/**
 * 描述：支付宝授权
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface AlipayAuthContract {
    public interface Model extends BaseModel {
        Observable<ResultData2<String>> getAuthInfo4Ali(
                String uuid,
                String token);

        Observable<ResultData2<User>> bindingUserInfoByAli(
                String uuid,
                String code,
                String token);

    }

    interface View extends BaseView {
        void returnAuthInfo(String authInfo);

        void successBindingAli(User user);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getAuthInfo4Ali(String uuid);

        public abstract void bindingUserInfoByAli(String uuid, String code);
    }
}
