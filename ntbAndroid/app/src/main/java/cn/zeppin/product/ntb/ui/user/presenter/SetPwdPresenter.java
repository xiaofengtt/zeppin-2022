package cn.zeppin.product.ntb.ui.user.presenter;

import android.text.TextUtils;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.SetPwdContract;

/**
 * 描述：设置密码
 * 开发人: geng
 * 创建时间: 17/12/13
 */

public class SetPwdPresenter extends SetPwdContract.Presenter {
    @Override
    public void setPwd(final String uuid, final String newPwd, final String oldPwd) {
        mView.showLoading("");
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    //是否已经设置过密码，是则进入"设置密码"，否则进入"修改密码"
                    String token = TextUtils.isEmpty(oldPwd) ? ApiConstants.setUserPwdToken(newPwd, resultData2.getData()) :
                            ApiConstants.editUserPwdToken(newPwd, oldPwd, resultData2.getData());

                    mRxManage.add(mModel.serPwd(uuid
                            , ApiConstants.getToken(uuid, resultData2.getData())
                            , token).subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData2 resultData2) {
                            if (resultData2.success()) {
                                //设置成功
                                mView.setPwdSuccess();
                            } else {
                                mView.showErrorTip(resultData2.getMessage());
                            }
                            mView.stopLoading();
                        }

                        @Override
                        protected void _onError(String message) {
                            mView.stopLoading();
                            mView.showErrorTip(message);
                        }
                    }));
                } else {
                    mView.showErrorTip(resultData2.getMessage());
                    mView.stopLoading();
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
