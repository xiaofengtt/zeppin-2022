package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.MyBankDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.MyBankDetailContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：我的银行卡详细信息
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class MyBankDetailModel implements MyBankDetailContract.Model {
    @Override
    public Observable<ResultData2<MyBankDetail>> bindingCardInfo(String uuid, String bankcard,String token) {
        return Api.getDefault().bindingCardInfo(uuid, bankcard, token)
                .map(new Func1<ResultData2, ResultData2<MyBankDetail>>() {
                    @Override
                    public ResultData2<MyBankDetail> call(ResultData2 resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<MyBankDetail>>io_main());
    }
}
