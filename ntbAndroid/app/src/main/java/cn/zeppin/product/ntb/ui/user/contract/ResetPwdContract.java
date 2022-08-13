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

public interface ResetPwdContract {
    interface Model extends BaseModel {
        Observable<ResultData2> resetPwd(String token, String phone);

    }

    interface View extends BaseView {
        void resetSuccess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void resetPwd(String pwd, String phone, String code);
    }
}
