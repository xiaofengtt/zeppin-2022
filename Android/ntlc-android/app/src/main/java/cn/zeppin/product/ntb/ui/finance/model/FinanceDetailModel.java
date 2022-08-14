package cn.zeppin.product.ntb.ui.finance.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.FinanceDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.FinanceDetailContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：持仓详情
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinanceDetailModel implements FinanceDetailContract.Model {

    @Override
    public Observable<ResultData2<FinanceDetail>> getFinanceInfoModel(String uuid, String financialUuid, String token) {
        return Api.getDefault().getFinanceInfo(uuid, financialUuid, token)
                .map(new Func1<ResultData2<FinanceDetail>, ResultData2<FinanceDetail>>() {
                    @Override
                    public ResultData2<FinanceDetail> call(ResultData2<FinanceDetail> resultData) {
                        return resultData;
                    }
                }).compose(RxSchedulers.<ResultData2<FinanceDetail>>io_main());
    }
}
