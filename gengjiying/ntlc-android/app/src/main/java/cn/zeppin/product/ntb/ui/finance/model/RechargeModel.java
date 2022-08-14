package cn.zeppin.product.ntb.ui.finance.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.RechargeContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：充值
 * 开发人: geng
 * 创建时间: 17/10/24
 */

public class RechargeModel extends CommonModel implements RechargeContract.Model {
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
    public Observable<ResultData2<String>> getAuthInfo4Ali(String uuid, String token) {
        return Api.getDefault().getAuthInfo4Ali(uuid, token).map(new Func1<ResultData2<String>, ResultData2<String>>() {
            @Override
            public ResultData2<String> call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }
}
