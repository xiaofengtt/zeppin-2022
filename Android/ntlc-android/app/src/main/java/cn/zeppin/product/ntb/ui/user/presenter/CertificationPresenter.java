package cn.zeppin.product.ntb.ui.user.presenter;

import android.net.Uri;

import com.geng.library.baserx.RxSubscriber;

import java.io.File;

import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ImgResource;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.CertificationContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;

/**
 * 描述：实名认证
 * 开发人: geng
 * 创建时间: 17/10/9
 */

public class CertificationPresenter extends CertificationContract.Presenter {
    @Override
    public void uploadImage(final File file, final boolean isFace, final Uri uri) {
        mView.showLoading("上传中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.uploadImage(file, ApiConstants.getToken(AppApplication.getInstance().getUuid(), serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2<ImgResource>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<ImgResource> resultData2) {
                                if (resultData2.success()) {
                                    //上传成功
                                    if (isFace) {//正面
                                        mView.loadImgface(resultData2.getData(), uri);
                                    } else {//反面
                                        mView.loadImgBack(resultData2.getData(), uri);
                                    }
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
            }

            @Override
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });
    }

    @Override
    public void certification(final String uuid, final String name, final String idcard, final String imgface, final String imgback) {
        mView.showLoading(mContext.getResources().getString(R.string.loading));
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.certification(uuid, EncryptUtil.getBase64(name), EncryptUtil.getBase64(idcard), imgface, imgback,
                        ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {//提交成功
                                    mView.submitSuccess();
                                } else {
                                    mView.submitFailed(resultData2.getMessage());
                                }
                                mView.stopLoading();
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                                mView.stopLoading();
                            }
                        }));
            }

            @Override
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });
    }

    @Override
    public void certificationImg(final String uuid, final String imgface, final String imgback) {
        mView.showLoading(mContext.getResources().getString(R.string.loading));
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.certificationImg(uuid, imgface, imgback, ApiConstants.getToken(uuid, serverTimestamp))
                        .subscribe(new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                //证件照认证成功
                                if (resultData2.success()) {//提交成功
                                    mView.submitSuccess();
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
            }

            @Override
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });
    }
}
