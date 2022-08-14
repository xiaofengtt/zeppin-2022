package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.ContextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.ui.user.adapter.BankAdapter;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.widget.RecyclerViewDivider;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BANK;

/**
 * 选择银行
 */
public class BankActivity extends BaseActivity implements BaseActivity.IRefreshListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bank_recyclerView)
    RecyclerView bankRecyclerView;

    private BankAdapter mAdapter;
    private List<Bank> mList = new ArrayList<>();
    private Bank currentBank;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("选择银行");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentBank = (Bank) bundle.getSerializable(INTENT_BANK);
        }

        bankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bankRecyclerView.addItemDecoration(new RecyclerViewDivider(BankActivity.this, LinearLayoutManager.VERTICAL, ContextUtils.dip2px(this, 1), ContextCompat.getColor(this, R.color.list_bg)));

        mAdapter = new BankAdapter(mList, currentBank);
        bankRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(INTENT_BANK, mList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        initData();
    }

    /**
     * 获取所有银行信息列表
     *
     * @param banks
     */
    private void returnBankList(List<Bank> banks) {
        mList.clear();
        if (banks != null && banks.size() > 0) {
            mList.addAll(banks);
        } else {
            mAdapter.setEmptyView(emptylayoutResId(this));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void toFailed() {
        mAdapter.setEmptyView(netErrorlayoutResId(this));
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        startProgressDialog();
        mRxManager.add(getBankList().subscribe(new RxSubscriber<List<Bank>>(this, false) {
            @Override
            protected void _onNext(List<Bank> banks) {
                returnBankList(banks);
                stopProgressDialog();
            }

            @Override
            protected void _onError(String message) {
                toFailed();
                stopProgressDialog();
            }
        }));
    }


    private Observable<List<Bank>> getBankList() {
        return Api.getDefault()
                .getBankList("web/product/bankList?flagBinding=true" + "&device=" + EncryptUtil.getBase64(ApiConstants.ANDROID))
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

    @Override
    public void toRefresh() {

    }
}