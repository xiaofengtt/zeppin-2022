package cn.zeppin.product.ntb.ui.user.fragment;

import android.support.v7.widget.RecyclerView;

import com.geng.library.commonutils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.base.BaseFragment;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.ui.user.adapter.CouponAdapter;
import cn.zeppin.product.ntb.ui.user.contract.CouponContract;
import cn.zeppin.product.ntb.ui.user.model.CouponModel;
import cn.zeppin.product.ntb.ui.user.presenter.CouponPresenter;

/**
 * 描述：优惠券--历史
 * 开发人: geng
 * 创建时间: 17/12/7
 */

public class CouponHistoryFragment extends BaseFragment<CouponPresenter, CouponModel> implements CouponContract.View, BaseFragment.IRefreshListener {
    @Bind(R.id.coupon_recyclerView)
    RecyclerView couponRecyclerView;

    private CouponAdapter mAdapter;
    private List<Coupon> mList = new ArrayList<>();
    private String uuid = AppApplication.getInstance().getUuid();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_coupon_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        mAdapter = new CouponAdapter(mList, false, null);
        couponRecyclerView.setAdapter(mAdapter);
        loadData();
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
        mAdapter.setEmptyView(emptylayoutResId(R.drawable.ic_net_off, getResources().getString(R.string.net_off), this));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnCouponList(List<Coupon> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.empty_coupon, "暂时没有历史优惠券"));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void toRefresh() {
        if (NetWorkUtils.isNetConnected(getContext())) {
            mList.clear();
            loadData();
        } else {
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.ic_net_off, getResources().getString(R.string.net_off), this));
            mAdapter.notifyDataSetChanged();
            stopLoading();
        }
    }

    private void loadData() {
        mPresenter.getCouponList(uuid, ApiConstants.CouponStatus.HISTORY, "");
    }
}
