package cn.zeppin.product.ntb.base;

import android.content.Context;

import com.geng.library.baserx.RxManager;
import com.geng.library.baserx.RxSubscriber;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.ResultData2;

/**
 * des:基类presenter
 * Created by xsf
 * on 2016.07.11:55
 */
public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {

    }

    public void getServerTimestamp(final TimeListener timeListener) {
        mRxManage.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    timeListener.onSuccess(resultData2.getData());
                }
            }

            @Override
            protected void _onError(String message) {
                timeListener.onError(message);
            }
        }));
    }

    public interface TimeListener {
        void onSuccess(String serverTimestamp);

        void onError(String message);
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
