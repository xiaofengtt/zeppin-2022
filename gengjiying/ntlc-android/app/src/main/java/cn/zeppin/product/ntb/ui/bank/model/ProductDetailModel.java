package cn.zeppin.product.ntb.ui.bank.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.ProductDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.bank.contract.ProductDetailContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：银行理财详情
 * 开发人: geng
 * 创建时间: 17/9/7
 */

public class ProductDetailModel implements ProductDetailContract.Model {

    @Override
    public Observable<ProductDetail> getProductDetail(String uuid) {
        return Api.getDefault()
                .getProductDetail(uuid, EncryptUtil.getBase64(ApiConstants.ANDROID))
                .map(new Func1<ResultData2<ProductDetail>, ProductDetail>() {
                    @Override
                    public ProductDetail call(ResultData2<ProductDetail> resultData) {
                        return resultData.getData();
                    }
                })
                .compose(RxSchedulers.<ProductDetail>io_main());
    }
}
