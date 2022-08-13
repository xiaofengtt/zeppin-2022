package cn.zeppin.product.ntb.ui.bank.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.bean.ProductDetail;
import cn.zeppin.product.ntb.ui.bank.contract.ProductDetailContract;

/**
 * 描述：银行理财详情
 * 开发人: geng
 * 创建时间: 17/9/20
 */

public class ProduceDetailPresenter extends ProductDetailContract.Presenter {
    @Override
    public void getProductDetail(String uuid) {
        mRxManage.add(mModel.getProductDetail(uuid).subscribe(new RxSubscriber<ProductDetail>(mContext, false) {
            @Override
            protected void _onNext(ProductDetail productDetail) {
                mView.returnProductDetail(productDetail);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
