package cn.zeppin.product.ntb.ui.finance.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.finance.contract.FinanceContract;


/**
 * 描述：我的持仓
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinancePresenter extends FinanceContract.Presenter {
    @Override
    public void getFinanceList(final String uuid, final String stage, final int pageNum, final int pageSize, final String sorts) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.getFinanceList(uuid, stage, pageNum, pageSize, sorts, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData<Finance>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData<Finance> resultData) {
                                    if (resultData.success()) {
                                        mView.stopLoading();
                                        mView.returnFinanceList(resultData.getData(), resultData.getTotalResultCount());
                                    } else {
                                        mView.stopLoading();
//                                        mView.showErrorTip(resultData.getMessage());
                                        mView.returnFinanceList(null, resultData.getTotalResultCount());
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.stopLoading();
                                    mView.showErrorTip(message);
                                }
                            }));

                } else {
                    mView.stopLoading();
                    mView.showErrorTip(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
