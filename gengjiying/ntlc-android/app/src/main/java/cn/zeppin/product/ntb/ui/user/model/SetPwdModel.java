package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.SetPwdContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：设置密码
 * 开发人: geng
 * 创建时间: 17/12/13
 */

public class SetPwdModel implements SetPwdContract.Model {
    @Override
    public Observable<ResultData2> serPwd(String uuid, String token, String encrypt) {
        return Api.getDefault().setUserPwd(uuid, token, encrypt).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2 call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
