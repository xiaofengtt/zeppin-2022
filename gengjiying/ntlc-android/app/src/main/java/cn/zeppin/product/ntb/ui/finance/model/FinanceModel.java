package cn.zeppin.product.ntb.ui.finance.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.finance.contract.FinanceContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：我的持仓
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinanceModel implements FinanceContract.Model {
    @Override
    public Observable<ResultData<Finance>> getFinanceList(String uuid, String stage, int pageNum, int pageSize, String sorts, String token) {
        return Api.getDefault().getFinanceList(uuid, stage, pageNum, pageSize, sorts, token)
                .map(new Func1<ResultData<Finance>, ResultData<Finance>>() {
                    @Override
                    public ResultData<Finance> call(ResultData<Finance> resultData) {
                        return resultData;
                    }
                }).compose(RxSchedulers.<ResultData<Finance>>io_main());
    }
}
