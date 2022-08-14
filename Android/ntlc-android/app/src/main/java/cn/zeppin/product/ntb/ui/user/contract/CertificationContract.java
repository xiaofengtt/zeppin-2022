package cn.zeppin.product.ntb.ui.user.contract;

import android.net.Uri;

import com.geng.library.base.BaseView;

import java.io.File;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.ImgResource;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：实名认证
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface CertificationContract {
    interface Model extends BaseModel {
        Observable<ResultData2<ImgResource>> uploadImage(File file, String token);

        Observable<ResultData2> certification(String uuid, String name, String idcard, String imgface, String imgback, String token);

        Observable<ResultData2> certificationImg(String uuid, String imgface, String imgback, String token);
    }

    interface View extends BaseView {
        void loadImgface(ImgResource resource, Uri uri);

        void loadImgBack(ImgResource resource, Uri uri);

        void submitSuccess();

        void submitFailed(String msg);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void uploadImage(File file, boolean isFace, Uri uri);

        public abstract void certification(String uuid, String name, String idcard, String imgface, String imgback);

        public abstract void certificationImg(String uuid, String imgface, String imgback);

    }
}
