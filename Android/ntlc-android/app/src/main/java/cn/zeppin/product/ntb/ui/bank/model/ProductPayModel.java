package cn.zeppin.product.ntb.ui.bank.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.bank.contract.ProductPayContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：购买理财
 * 开发人: geng
 * 创建时间: 17/10/25
 */

public class ProductPayModel extends CommonModel implements ProductPayContract.Model {
    //    @Override
//    public Observable<ResultData2> sendCodeById(String uuid, String token) {
//        return Api.getDefault().sendCodeById(uuid, token).map(new Func1<ResultData2, ResultData2>() {
//            @Override
//            public ResultData2<User> call(ResultData2 registerResultData2) {
//                return registerResultData2;
//            }
//        }).compose(RxSchedulers.<ResultData2>io_main());
//    }


    @Override
    public Observable<ResultData2<String>> sendCodeById(String uuid, String token, String price, String product) {
        return Api.getDefault().sendCodeById(uuid, token, price, product).map(new Func1<ResultData2<String>, ResultData2<String>>() {
            @Override
            public ResultData2<String> call(ResultData2<String> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }

    @Override
    public Observable<ResultData2<String>> productPay(String uuid, String price, String product, String type, String code, String coupon, String token) {
        return Api.getDefault().productPay(uuid, price, product, type, code, coupon, token)
                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
                    @Override
                    public ResultData2<String> call(ResultData2<String> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }
//
//    @Override
//    public Observable<ResultData2<User>> getInfo(String uuid, String token) {
//        return super.getInfo(uuid, token);
//    }
}
