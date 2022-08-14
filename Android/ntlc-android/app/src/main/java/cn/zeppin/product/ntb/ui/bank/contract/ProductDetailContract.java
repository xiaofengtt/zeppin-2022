package cn.zeppin.product.ntb.ui.bank.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ProductDetail;
import rx.Observable;

/**
 * Created by geng on 17/9/6.
 * class: 理财产品详情
 */

public interface ProductDetailContract {
    interface Model extends BaseModel {
        /**
         * 银行理财产品uuid
         *
         * @param uuid
         * @return
         */
        Observable<ProductDetail> getProductDetail(String uuid);

    }

    interface View extends BaseView {
        void returnProductDetail(ProductDetail data);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getProductDetail(String uuid);
    }
}
