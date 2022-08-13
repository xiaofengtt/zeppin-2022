package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/9/28
 */

public class ResetPwdPresenter extends ResetPwdContract.Presenter {
    @Override
    public void resetPwd(final String pwd, final String phone, final String code) {
        mView.showLoading("");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManage.add(mModel.resetPwd(ApiConstants.getResetPwdToken(pwd, phone, code, resultData2.getData()), EncryptUtil.getBase64(phone))
                            .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2 resultData2) {
                                    if (resultData2.success()) {
                                        mView.resetSuccess();
                                    } else {
                                        mView.showErrorTip(resultData2.getMessage());
                                    }
                                    mView.stopLoading();
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.showErrorTip(message);
                                    mView.stopLoading();
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
