package cn.zeppin.product.ntb.ui.main.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.main.contract.GiftContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：大礼包
 * 开发人: geng
 * 创建时间: 17/12/19
 */

public class GiftModel implements GiftContract.Model {
    @Override
    public Observable<ResultData2<Gift>> getNotActiveList(String uuid, String token) {
        return Api.getDefault().getNotActiveList(uuid, token)
                .map(new Func1<ResultData2<Gift>, ResultData2<Gift>>() {
                    @Override
                    public ResultData2<Gift> call(ResultData2<Gift> couponResultData) {
                        return couponResultData;
                    }
                }).compose(RxSchedulers.<ResultData2<Gift>>io_main());
    }

    @Override
    public Observable<ResultData2<String>> receiveActivate(String uuid, String token, String coupons, boolean flagShare, String redPackets) {
        return Api.getDefault().receiveActivate(uuid, token, coupons, flagShare, redPackets)
                .map(new Func1<ResultData2<String>, ResultData2<String>>() {
                    @Override
                    public ResultData2<String> call(ResultData2<String> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }
}
