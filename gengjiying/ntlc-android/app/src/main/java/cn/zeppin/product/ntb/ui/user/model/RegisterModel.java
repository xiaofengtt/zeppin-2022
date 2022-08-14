package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.RegisterContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：注册
 * 开发人: geng
 * 创建时间: 17/9/25
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<ResultData2<User>> register(String token, String phone) {
        return Api.getDefault().register(token, phone).map(new Func1<ResultData2<User>, ResultData2<User>>() {
            @Override
            public ResultData2<User> call(ResultData2<User> registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2<User>>io_main());
    }

    @Override
    public Observable<ResultData2> sendCode(String phone,String codeType,String token) {
        return Api.getDefault().sendCode(phone, codeType,token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2<User> call(ResultData2 registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
