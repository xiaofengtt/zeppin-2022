package cn.zeppin.product.ntb.ui.user.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.bean.Message;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：消息
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface MessageContract {
    public interface Model extends BaseModel {
        /**
         * 获取消息列表
         *
         * @param uuid     用户编号
         * @param pageNum  页码
         * @param pageSize 条数
         * @param token    公共的token
         * @return
         */
        Observable<ResultData<Message>> getMessageList(
                String uuid,
                int pageNum,
                int pageSize,
                String token);

        /**
         * 设置为已读
         *
         * @param uuid  消息编号
         * @param token 公共的token
         * @return
         */
        Observable<ResultData2<Message>> setRead(String uuid, String token,String infoUuid);

        /**
         * 设置全部已读
         *
         * @param uuid
         * @param token
         * @return
         */
        Observable<ResultData2> setAllRead(String uuid, String token);
    }

    interface View extends BaseView {
        void returnMessageList(List<Message> list, int totalPageCount);

        void returnReadStatus(boolean isRead);

        void returnAllReadStatus(boolean isRead);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getMessageList(String uuid, int pageNum, int pageSize);

        public abstract void setRead(String infoUuid, String userUuid);

        public abstract void setAllRead(String uuid);
    }
}
