package cn.zeppin.product.ntb.ui.finance.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.commonutils.NetWorkUtils;
import com.geng.library.commonutils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.base.BaseFragment;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.ui.finance.activity.FinanceDetailActivity;
import cn.zeppin.product.ntb.ui.finance.adapter.FinishAdapter;
import cn.zeppin.product.ntb.ui.finance.contract.FinanceContract;
import cn.zeppin.product.ntb.ui.finance.model.FinanceModel;
import cn.zeppin.product.ntb.ui.finance.presenter.FinancePresenter;
import cn.zeppin.product.ntb.widget.WrapContentLinearLayoutManager;
import rx.functions.Action1;

/**
 * 已完成
 */
public class FinishFragment extends BaseFragment<FinancePresenter, FinanceModel> implements FinanceContract.View, BaseFragment.IRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private FinishAdapter mAdapter;
    private List<Finance> mList = new ArrayList<>();

    private int mStartPage = 1;
    private int totalResultCount;

    private final int PAGESIZE = 100;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hold;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new FinishAdapter(mList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstant.INTENT_FINANCE_UUID, mAdapter.getData().get(position).getUuid());
                    startActivity(FinanceDetailActivity.class, bundle);
                }
            }
        });
        //退出登录
        mRxManager.on(AppConstant.LOGIN_OUT, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                if (mList != null && mList.size() > 0) {
                    mList.clear();
                    mStartPage = 1;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        mRxManager.on(AppConstant.REFRESH_FINANCE_FINISH, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                reloadData();
            }
        });
    }

    private void loadData() {

        showLoading("加载中...");
        mPresenter.getFinanceList(AppApplication.getInstance().getUuid(), ApiConstants.Finance.FINISHED, mStartPage, PAGESIZE, "");
    }


    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.showToastCenter(msg);
        mAdapter.setEmptyView(netErrorlayoutResId(this));
        mAdapter.loadMoreComplete();
    }

    @Override
    public void returnFinanceList(List<Finance> list, int totalResultCount) {
        mList.clear();
        this.totalResultCount = totalResultCount;
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            mStartPage++;
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId(this));
        }
        mAdapter.loadMoreComplete();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void toRefresh() {
        startProgressDialog();
        if (NetWorkUtils.isNetConnected(getContext())) {

            reloadData();
        } else {
            stopLoading();
            netError();
        }
    }

    private void netError() {
        if (mAdapter != null) {
            mAdapter.setEmptyView(netErrorlayoutResId(this));
            mAdapter.notifyDataSetChanged();
        } else {
//            showNetErrorTip();
            ToastUitl.showToastCenter(getText(R.string.no_net).toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        mStartPage = 1;
        loadData();
    }
}