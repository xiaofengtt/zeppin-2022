package com.happyxmall.www.ui.presenter;

import android.util.Log;

import com.happyxmall.www.bean.ApiConstants;
import com.happyxmall.www.bean.ResultData;
import com.happyxmall.www.ui.contract.LoginContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void checkin(String uniqueToken, String uuid, String deviceToken) {
        getServerTimestamp(new TimeListener() {
            @Override
            public void onSuccess(String serverTimestamp) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mModel.checkin(uniqueToken, ApiConstants.getCheckinToken(uuid,serverTimestamp), deviceToken).subscribe(new Observer<ResultData<String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeSubscription.add(d);
                            }

                            @Override
                            public void onNext(ResultData<String> resultData) {
                                if (resultData.success()) {
                                    Log.i("checkin","deviceToken checkin successful!!!");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.e("checkin","deviceToken checkin System Error!!!");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onError(String message) {
                Log.e("getServerTimestamp","getServerTimestamp System Error "+message);
            }
        });
    }
}
