package cn.zeppin.product.ntb.ui.finance.contract;

import com.geng.library.base.BaseView;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.FinanceDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import rx.Observable;

/**
 * 描述：持仓详情
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface FinanceDetailContract {
    interface Model extends BaseModel {
        Observable<ResultData2<FinanceDetail>> getFinanceInfoModel(
                String uuid,
                String financialUuid,
                String token);
    }

    interface View extends BaseView {
        void returnFinanceInfo(FinanceDetail data);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getFinanceInfoPresenter(final String uuid, final String financialUuid);
    }
}
