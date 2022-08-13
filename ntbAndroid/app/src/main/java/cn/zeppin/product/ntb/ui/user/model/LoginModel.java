package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.LoginContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：登录
 * 开发人: geng
 * 创建时间: 17/9/26
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<ResultData2<User>> login(String token) {
        return Api.getDefault().login(token).map(new Func1<ResultData2<User>, ResultData2<User>>() {
            @Override
            public ResultData2<User> call(ResultData2<User> registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2<User>>io_main());
    }

    @Override
    public Observable<ResultData2<User>> loginBycode(String token) {
        return Api.getDefault().loginBycode(token).map(new Func1<ResultData2<User>, ResultData2<User>>() {
            @Override
            public ResultData2<User> call(ResultData2<User> registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2<User>>io_main());
    }
}
