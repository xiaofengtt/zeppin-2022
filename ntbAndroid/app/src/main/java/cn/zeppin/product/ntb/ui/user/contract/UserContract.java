package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

/**
 * 描述：支付宝授权
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface UserContract {
    public interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
    }
}
