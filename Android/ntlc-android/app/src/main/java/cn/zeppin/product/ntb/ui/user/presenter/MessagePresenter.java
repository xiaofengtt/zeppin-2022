package cn.zeppin.product.ntb.ui.user.presenter;

import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Message;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.MessageContract;

/**
 * 描述：消息
 * 开发人: geng
 * 创建时间: 17/12/11
 */

public class MessagePresenter extends MessageContract.Presenter {
    @Override
    public void getMessageList(final String uuid, final int pageNum, final int pageSize) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.getMessageList(uuid, pageNum, pageSize, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData<Message>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData<Message> messageResultData) {
                                if (messageResultData.success()) {
                                    mView.returnMessageList(messageResultData.getData(), messageResultData.getTotalPageCount());
                                } else {
                                    mView.showErrorTip(messageResultData.getMessage());
                                }
                                mView.stopLoading();
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.showErrorTip(message);
                                mView.stopLoading();
                            }
                        }
                ));
            }

            @Override
            public void onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });

    }

    @Override
    public void setRead(final String infoUuid, final String userUuid) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.setRead(userUuid, ApiConstants.getToken(userUuid, serverTimestamp), infoUuid).subscribe(
                        new RxSubscriber<ResultData2<Message>>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2<Message> messageResultData2) {
                                if (messageResultData2.success()) {
                                    mView.returnReadStatus(true);
                                } else {
                                    mView.returnReadStatus(false);
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.returnReadStatus(false);
                            }
                        }
                ));
            }

            @Override
            public void onError(String message) {
                mView.returnReadStatus(false);
            }
        });
    }

    @Override
    public void setAllRead(final String uuid) {
        mView.showLoading("加载中...");
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                mRxManage.add(mModel.setAllRead(uuid, ApiConstants.getToken(uuid, serverTimestamp)).subscribe(
                        new RxSubscriber<ResultData2>(mContext, false) {
                            @Override
                            protected void _onNext(ResultData2 resultData2) {
                                if (resultData2.success()) {
                                    mView.returnAllReadStatus(true);
                                } else {
                                    mView.returnAllReadStatus(false);
                                    mView.showErrorTip(resultData2.getMessage());
                                }
                                mView.stopLoading();
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.returnAllReadStatus(false);
                                mView.stopLoading();
                                mView.showErrorTip(message);
                            }
                        }
                ));
            }

            @Override
            public void onError(String message) {
                mView.returnAllReadStatus(false);
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        });
    }
}
