package cn.zeppin.product.ntb.ui.user.model;

import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.AlipayAuthContract;
import rx.Observable;

/**
 * 描述：支付宝授权
 * 开发人: geng
 * 创建时间: 17/11/21
 */

public class AlipayAuthModel extends PayModel implements AlipayAuthContract.Model {
    @Override
    public Observable<ResultData2<String>> getAuthInfo4Ali(String uuid, String token) {
        return super.getAuthInfo4Ali(uuid, token);
    }

    @Override
    public Observable<ResultData2<User>> bindingUserInfoByAli(String uuid, String code, String token) {
        return super.bindingUserInfoByAli(uuid, code, token);
    }
}
