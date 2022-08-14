package cn.zeppin.product.ntb.ui.bank.model;

import com.geng.library.baserx.RxSchedulers;

import java.util.List;

import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.bank.contract.BankContract;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import rx.Observable;
import rx.functions.Func1;

/**
 * 描述：银行理财
 * 开发人: geng
 * 创建时间: 17/9/7
 */

public class BankModel implements BankContract.Model {
    @Override
    public Observable<ResultData<Product>> getProductList(String name, String custodian, String term, String sorts, int pageNum, int pageSize) {
        return Api.getDefault()
                .getProductList(name, custodian, term, sorts, pageNum, pageSize, EncryptUtil.getBase64(ApiConstants.ANDROID))
                .map(new Func1<ResultData<Product>, ResultData<Product>>() {
                    @Override
                    public ResultData<Product> call(ResultData<Product> resultData) {
                        return resultData;
                    }
                })
                .compose(RxSchedulers.<ResultData<Product>>io_main());
    }

    @Override
    public Observable<List<Bank>> getBankList() {
        return Api.getDefault()
                .getBankList("web/product/bankList" + "?device=" + EncryptUtil.getBase64(ApiConstants.ANDROID))
                .map(new Func1<ResultData<Bank>, List<Bank>>() {
                    @Override
                    public List<Bank> call(ResultData<Bank> resultData) {
                        if (resultData.success()) {
                            return resultData.getData();
                        }
                        return null;
                    }
                }).compose(RxSchedulers.<List<Bank>>io_main());
    }
}
