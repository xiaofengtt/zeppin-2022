package cn.zeppin.product.ntb.ui.bank.contract;

import com.geng.library.base.BaseModel;
import com.geng.library.base.BasePresenter;
import com.geng.library.base.BaseView;

import java.util.List;

import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.bean.ResultData;
import rx.Observable;

/**
 * Created by geng on 17/9/6.
 * class:
 */

public interface BankContract {
    interface Model extends BaseModel {
        Observable<ResultData<Product>> getProductList(String name,
                                                       String custodian,
                                                       String term,
                                                       String sorts,
                                                       int pageNum,
                                                       int pageSize);

        Observable<List<Bank>> getBankList();
    }

    interface View extends BaseView{
        void returnProductList(List<Product> list, int totalPageCount);
        void returnBankList(List<Bank> bankList);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void getProductList(String name,
                                            String custodian,
                                            String term,
                                            String sorts,
                                            int pageNum,
                                            int pageSize);
        public abstract void getBankList();
    }
}
