package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.UnBindBankContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：解绑银行卡
 * 开发人: geng
 * 创建时间: 17/10/11
 */

public class UnbindBankModel implements UnBindBankContract.Model {
    @Override
    public Observable<ResultData2> unbindSendCode(String uuid, String bankcard, String phone, String token) {
        return Api.getDefault().unbindSendCode(uuid, bankcard, phone, token)
                .map(new Func1<ResultData2, ResultData2>() {
                    @Override
                    public ResultData2 call(ResultData2 resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2>io_main());
    }

    @Override
    public Observable<ResultData2> unbindBankcard(String uuid, String phone, String code, String bankcard, String token) {
        return Api.getDefault().unbindBankcard(uuid, phone, code, bankcard, token)
                .map(new Func1<ResultData2, ResultData2>() {
                    @Override
                    public ResultData2 call(ResultData2 resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
