package com.happyxmall.www.ui.contract;

import android.view.View;

import com.happyxmall.www.base.BaseModel;
import com.happyxmall.www.base.BasePresenter;
import com.happyxmall.www.base.BaseView;
import com.happyxmall.www.bean.ResultData;

import io.reactivex.Observable;

public class LoginContract {
    public interface Model extends BaseModel {
        Observable<ResultData<String>> checkin(String uniqueToken, String token, String deviceToken);
    }
    public interface View extends BaseView {
        //void loginSuccess(User user);
    }
    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void checkin(String uniqueToken, String uuid, String deviceToken);
    }
}
