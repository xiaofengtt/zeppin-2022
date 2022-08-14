package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdCheckContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：重置密码验证
 * 开发人: geng
 * 创建时间: 17/9/26
 */

public class ResetPwdCheckModel implements ResetPwdCheckContract.Model {
    @Override
    public Observable<ResultData2> resetCheck(String token, String phone) {
        return Api.getDefault().resetCheck(token, phone).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2<User> call(ResultData2 registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }

    @Override
    public Observable<ResultData2> sendCode(String phone, String codeType, String token) {
        return Api.getDefault().sendCode(phone, codeType, token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2<User> call(ResultData2 registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
