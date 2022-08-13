package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.BindBankContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：绑定银行卡
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class BindBankModel implements BindBankContract.Model {
    @Override
    public Observable<ResultData2<String>> bindingSendCode(String uuid, String bankcard, String phone, String bank, String cardholder, String token) {
        return Api.getDefault().bindingSendCode(uuid,bankcard, phone, bank, cardholder, token)
                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
                    @Override
                    public ResultData2<String> call(ResultData2<String> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }

    @Override
    public Observable<ResultData2> bindingCard(String uuid, String bankcard, String phone, String code, String bank, String cardholder, String orderNum,String token) {
        return Api.getDefault().bindingCard(uuid, bankcard, phone, code, bank, cardholder, orderNum,token)
                .map(new Func1<ResultData2, ResultData2>() {
                    @Override
                    public ResultData2 call(ResultData2 resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
