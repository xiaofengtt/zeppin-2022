package cn.zeppin.product.ntb.ui.user.model;

import com.geng.library.baserx.RxSchedulers;

import java.io.File;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.bean.ImgResource;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.ui.user.contract.CertificationContract;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：实名认证
 * 开发人: geng
 * 创建时间: 17/10/9
 */

public class CertificationModel implements CertificationContract.Model {
    @Override
    public Observable<ResultData2<ImgResource>> uploadImage(File file, String token) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return Api.getDefault().upload("web/resource/add?token=" + token, body).map(new Func1<ResultData2<ImgResource>, ResultData2<ImgResource>>() {
            @Override
            public ResultData2<ImgResource> call(ResultData2<ImgResource> resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<ImgResource>>io_main());
    }

    @Override
    public Observable<ResultData2> certification(String uuid, String name, String idcard, String imgface, String imgback, String token) {
        return Api.getDefault().certification(uuid, name, idcard, imgface, imgback, token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2 call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }

    @Override
    public Observable<ResultData2> certificationImg(String uuid, String imgface, String imgback, String token) {
        return Api.getDefault().certificationImg(uuid, imgface, imgback, token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2 call(ResultData2 resultData2) {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }
}
