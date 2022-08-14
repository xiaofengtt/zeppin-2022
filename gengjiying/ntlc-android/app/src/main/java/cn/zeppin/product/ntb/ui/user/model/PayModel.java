package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.PayContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：支付宝
 * 开发人: geng
 * 创建时间: 17/11/21
 */

public class PayModel extends CommonModel implements PayContract.Model {

    @Override
    public Observable<ResultData<PaymentList>> getPaymentList(String uuid, int pageNum, int pageSize, String sorts, String token) {
        return Api.getDefault().getPaymentList(uuid, pageNum, pageSize, sorts, token).map(new Func1<ResultData<PaymentList>, ResultData<PaymentList>>() {
            @Override
            public ResultData<PaymentList> call(ResultData<PaymentList> resultData) {
                return resultData;
            }
        }).compose(RxSchedulers.<ResultData<PaymentList>>io_main());
    }

    @Override
    public Observable<ResultData2<String>> getAuthInfo4Ali(String uuid, String token) {
        return Api.getDefault().getAuthInfo4Ali(uuid, token).map(new Func1<ResultData2<String>, ResultData2<String>>() {
            @Override
            public ResultData2<String> call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }

    @Override
    public Observable<ResultData2<User>> bindingUserInfoByAli(String uuid, String code, String token) {
        return Api.getDefault().bindingUserInfoByAli(uuid, code, token).map(new Func1<ResultData2<User>, ResultData2<User>>() {
            @Override
            public ResultData2<User> call(ResultData2<User> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<User>>io_main());
    }

    @Override
    public Observable<ResultData<AlipayCode>> getAliQrCode(String uuid, String token) {
        return Api.getDefault().getAliQrCode(Api.getCacheControl(),uuid, token).map(new Func1<ResultData<AlipayCode>, ResultData<AlipayCode>>() {
            @Override
            public ResultData<AlipayCode> call(ResultData<AlipayCode> resultData) {
                return resultData;
            }
        }).compose(RxSchedulers.<ResultData<AlipayCode>>io_main());
    }

    @Override
    public Observable<ResultData2<String>> rechargeByChanpay(String uuid, String price, String bankcard, String type, String body, String code, String orderNum, String token) {
        return Api.getDefault().rechargeByChanpay(uuid, price, bankcard, type, body, code, orderNum, token)
                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
                    @Override
                    public ResultData2<String> call(ResultData2<String> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }

    @Override
    public Observable<ResultData<MyBank>> getBindingCard(String uuid, String token) {
        return super.getBindingCard(uuid, token);
    }

    @Override
    public Observable<ResultData2<User>> getUserInfo(String uuid, String token) {
        return super.getInfo(uuid,token);
    }
}
