package cn.zeppin.product.ntb.ui.finance.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.FinanceDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.FinanceDetailContract;


/**
 * 描述：持仓详情
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinanceDetailPresenter extends FinanceDetailContract.Presenter {
    @Override
    public void getFinanceInfoPresenter(final String uuid, final String financialUuid) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getFinanceInfoModel(uuid, financialUuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData2<FinanceDetail>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<FinanceDetail> resultData) {
                                if (resultData.success()) {
                                    mView.returnFinanceInfo(resultData.getData());
                                } else {
                                    mView.showErrorTip(resultData.getMessage());
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                mView.showErrorTip(message);
            }
        });

    }
}
