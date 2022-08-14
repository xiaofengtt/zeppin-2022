package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：重置密码
 * 开发人: geng
 * 创建时间: 17/9/28
 */

public class ResetPwdModel implements ResetPwdContract.Model {

    @Override
    public Observable<ResultData2> resetPwd(String token, String phone) {
        return Api.getDefault().resetPwd(token, phone).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2<User> call(ResultData2 registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
