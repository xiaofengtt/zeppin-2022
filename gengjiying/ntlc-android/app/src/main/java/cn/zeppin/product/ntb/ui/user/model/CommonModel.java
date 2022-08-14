package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.UpdateVersionInfo;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/11/17
 */

public class CommonModel implements CommonContract.Model {
    @Override
    public Observable<ResultData2<String>> sendCodeById(String uuid, String token) {
        return Api.getDefault().sendCodeById(uuid, token).map(new Func1<ResultData2<String>, ResultData2<String>>() {
            @Override
            public ResultData2<String> call(ResultData2<String> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<String>>io_main());
    }

    @Override
    public Observable<ResultData<MyBank>> getBindingCard(String uuid, String token) {
        return Api.getDefault().getBindingCard(uuid, token).map(new Func1<ResultData<MyBank>, ResultData<MyBank>>() {
            @Override
            public ResultData<MyBank> call(ResultData<MyBank> resultData) {
                return resultData;
            }
        }).compose(RxSchedulers.<ResultData<MyBank>>io_main());
    }

    @Override
    public Observable<ResultData2<User>> getInfo(String uuid, String token) {
        return Api.getDefault().getUser(uuid, token).map(new Func1<ResultData2<User>, ResultData2<User>>() {
            @Override
            public ResultData2<User> call(ResultData2<User> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<User>>io_main());
    }

    @Override
    public Observable<ResultData2<Boolean>> getWebmarketSwitch(String token, String webmarket, String version) {
        return Api.getDefault().getWebmarketSwitch(token, webmarket, version).map(new Func1<ResultData2<Boolean>, ResultData2<Boolean>>() {
            @Override
            public ResultData2<Boolean> call(ResultData2<Boolean> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<Boolean>>io_main());
    }

    @Override
    public Observable<ResultData2<UpdateVersionInfo>> getVersionInfo(String token, String version) {
        return Api.getDefault().getVersionInfo(token, version).map(new Func1<ResultData2<UpdateVersionInfo>, ResultData2<UpdateVersionInfo>>() {
            @Override
            public ResultData2<UpdateVersionInfo> call(ResultData2<UpdateVersionInfo> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<UpdateVersionInfo>>io_main());
    }
}
