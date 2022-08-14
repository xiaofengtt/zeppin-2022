package cn.zeppin.product.ntb.ui.finance.contract;

import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.base.BaseModel;
import cn.zeppin.product.ntb.base.BasePresenter;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.bean.ResultData;
import rx.Observable;

/**
 * 描述：我的持仓
 * 开发人: geng
 * 创建时间: 17/9/22
 */

public interface FinanceContract {
    interface Model extends BaseModel {
        Observable<ResultData<Finance>> getFinanceList(
                String uuid,
                String stage,
                int pageNum,
                int pageSize,
                String sorts,
                String token);
    }

    interface View extends BaseView {
        void returnFinanceList(List<Finance> list, int totalResultCount);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getFinanceList(
                String uuid,
                String stage,
                int pageNum,
                int pageSize,
                String sorts);
    }
}
