package cn.zeppin.product.ntb.ui.finance.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.WithdrawalContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：提现
 * 开发人: geng
 * 创建时间: 17/10/24
 */

public class WithdrawalModel extends CommonModel implements WithdrawalContract.Model {
    @Override
    public Observable<ResultData2<String>> withdraw(String uuid, String price, String bankcard, String code, String token) {
        return Api.getDefault().withdraw(uuid, price, bankcard, code, token)
                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
                    @Override
                    public ResultData2<String> call(ResultData2<String> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }
//
//    @Override
//    public Observable<ResultData2<String>> sendCodeById(String uuid,String token) {
//        return Api.getDefault().sendCodeById(uuid, token)
//                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
//                    @Override
//                    public ResultData2<String> call(ResultData2<String> ResultData2) {
//                        return ResultData2;
//                    }
//                }).compose(RxSchedulers.<ResultData2<String>>io_main());
//    }
}
