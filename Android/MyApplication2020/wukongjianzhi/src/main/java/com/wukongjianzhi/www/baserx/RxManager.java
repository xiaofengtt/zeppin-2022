package com.wukongjianzhi.www.baserx;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by xsf
 * on 2016.08.14:50
 */
public class RxManager {
    /*管理Observables 和 Subscribers订阅*/
    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param m
     */
    public void add(Disposable m) {
        /*订阅管理*/
        mCompositeSubscription.add(m);
    }
    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.clear();// 取消所有订阅
    }
}
