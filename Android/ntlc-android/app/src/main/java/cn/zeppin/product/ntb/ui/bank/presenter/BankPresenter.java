package cn.zeppin.product.ntb.ui.bank.presenter;

import com.geng.library.baserx.RxSubscriber;

import java.util.List;

import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.bank.contract.BankContract;

/**
 * 描述：银行理财
 * 开发人: geng
 * 创建时间: 17/9/7
 */

public class BankPresenter extends BankContract.Presenter {
    @Override
    public void getProductList(String name, String custodian, String term, String sorts, int pageNum, int pageSize) {
        mRxManage.add(mModel.getProductList(name, custodian, term, sorts, pageNum, pageSize).subscribe(new RxSubscriber<ResultData<Product>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("加载中...");
            }

            @Override
            protected void _onNext(ResultData<Product> resultData) {
                mView.stopLoading();
                if (resultData.success()) {
                    mView.returnProductList(resultData.getData(), resultData.getTotalPageCount());
                }
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBankList() {
        mRxManage.add(mModel.getBankList().subscribe(new RxSubscriber<List<Bank>>(mContext, false) {
            @Override
            protected void _onNext(List<Bank> bankList) {
                mView.returnBankList(bankList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
