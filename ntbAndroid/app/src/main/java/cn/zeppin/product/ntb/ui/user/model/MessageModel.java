package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.Message;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.MessageContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：消息
 * 开发人: geng
 * 创建时间: 17/12/11
 */

public class MessageModel implements MessageContract.Model {
    @Override
    public Observable<ResultData<Message>> getMessageList(String uuid, int pageNum, int pageSize, String token) {
        return Api.getDefault().getMessageList(uuid, pageNum, pageSize, token).map(new Func1<ResultData<Message>, ResultData<Message>>() {
            @Override
            public ResultData<Message> call(ResultData<Message> messageResultData) {
                return messageResultData;
            }
        }).compose(RxSchedulers.<ResultData<Message>>io_main());
    }

    @Override
    public Observable<ResultData2<Message>> setRead(String uuid, String token,String infoUuid) {
        return Api.getDefault().getMessage(uuid, token,infoUuid).map(new Func1<ResultData2<Message>, ResultData2<Message>>() {
            @Override
            public ResultData2<Message> call(ResultData2<Message> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<Message>>io_main());
    }

    @Override
    public Observable<ResultData2> setAllRead(String uuid, String token) {
        return Api.getDefault().setMessageAllRead(uuid, token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2 call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
