package com.happyxmall.www.base;

import android.content.Context;
import android.util.Log;

import com.happyxmall.www.app.Api;
import com.happyxmall.www.bean.ResultData;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * des:基类presenter
 * Created by xsf
 * on 2016.07.11:55
 */
public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {

    }

    public void getServerTimestamp(final TimeListener timeListener) {
        Api.getTime().subscribe(new Observer<ResultData<String>>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeSubscription.add(d);
            }

            @Override
            public void onNext(ResultData<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    timeListener.onSuccess(String.valueOf(resultData2.getData()));
                    Log.i("系统时间","时间："+resultData2.toString());
                }
            }
            @Override
            public void onError(Throwable e) {
                timeListener.onError("System Error!");
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public interface TimeListener {
        void onSuccess(String serverTimestamp);

        void onError(String message);
    }

    public void onDestroy() {
        if(mCompositeSubscription != null){
            mCompositeSubscription.clear();
        }
    }
}
