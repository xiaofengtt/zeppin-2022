package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.UpdateVersionInfo;
import cn.zeppin.product.ntb.bean.User;
import rx.Observable;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/11/17
 */

public class CommonContract {
    public interface Model extends BaseModel {
        Observable<ResultData2<String>> sendCodeById(String uuid, String token);

        Observable<ResultData<MyBank>> getBindingCard(String uuid, String token);

        Observable<ResultData2<User>> getInfo(String uuid, String token);

        Observable<ResultData2<Boolean>> getWebmarketSwitch(String token, String webmarket, String version);

        Observable<ResultData2<UpdateVersionInfo>> getVersionInfo(String token, String version);

    }

    public interface View extends BaseView {
        void sendCodeSuccess();

        void sendCodeFailed(String message);

        void returnMyBankList(List<MyBank> list);

        void returnFailed(String error);

        void getUserInfo(User user);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void sendCodeById(String uuid);

        public abstract void getBindingCard(String uuid);

        public abstract void getUserInfo(String uuid);
    }
}
