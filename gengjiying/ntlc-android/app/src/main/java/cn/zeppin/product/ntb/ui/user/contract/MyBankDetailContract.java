package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.MyBankDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：我的银行卡列表获取
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface MyBankDetailContract {
    interface Model extends BaseModel {
        Observable<ResultData2<MyBankDetail>> bindingCardInfo(String uuid, String bankcard, String token);
    }

    interface View extends BaseView {
        void returnMyBankDetail(MyBankDetail data);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void bindingCardInfo(String uuid, String bankcard);
    }
}
