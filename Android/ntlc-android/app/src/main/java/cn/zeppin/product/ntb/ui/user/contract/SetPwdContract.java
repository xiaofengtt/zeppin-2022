package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：找回密码
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface SetPwdContract {
    interface Model extends BaseModel {
        Observable<ResultData2> serPwd(String uuid, String token, String encrypt);
    }

    interface View extends BaseView {
        void setPwdSuccess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void setPwd(String uuid, String newPwd, String oldPwd);
    }
}
