package cn.zeppin.product.ntb.ui.user.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.NetWorkUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.base.BaseFragment;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.ui.user.adapter.CouponAdapter;
import cn.zeppin.product.ntb.ui.user.contract.CouponContract;
import cn.zeppin.product.ntb.ui.user.model.CouponModel;
import cn.zeppin.product.ntb.ui.user.presenter.CouponPresenter;

/**
 * 描述：优惠券-可用
 * 开发人: geng
 * 创建时间: 17/12/7
 */

public class CouponAvailableFragment extends BaseFragment<CouponPresenter, CouponModel> implements CouponContract.View, BaseFragment.IRefreshListener {
    @Bind(R.id.coupon_recyclerView)
    RecyclerView couponRecyclerView;
    @Bind(R.id.ll_unUse)
    LinearLayout llUnUse;

    private CouponAdapter mAdapter;
    private List<Coupon> mList = new ArrayList<>();
    private String uuid = AppApplication.getInstance().getUuid();
    private String buyPrice = "";
    private Coupon currentCoupon;

    public static CouponAvailableFragment newInstance(String price, Coupon coupon) {
        Bundle args = new Bundle();
        args.putString("price", price);
        args.putSerializable("currentCoupon", coupon);
        CouponAvailableFragment fragment = new CouponAvailableFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        final Bundle bundle = getArguments();
        if (bundle != null) {
            String getPrice = bundle.getString("price");
            if (!TextUtils.isEmpty(getPrice)) {
                buyPrice = (BigDecimal.valueOf(Double.parseDouble(getPrice)).multiply(BigDecimal.valueOf(100))) + "";//以分为单位
            }
            currentCoupon = (Coupon) bundle.getSerializable("currentCoupon");
            llUnUse.setVisibility(View.VISIBLE);
        } else {
            llUnUse.setVisibility(View.GONE);
        }
        mAdapter = new CouponAdapter(mList, true, currentCoupon);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (bundle != null) {
                    RxBus.getInstance().post(AppConstant.SELECTED_COUPON, mList.get(position));//当前选择的优惠券
                }
            }
        });
        couponRecyclerView.setAdapter(mAdapter);

        loadData();

        //不使用优惠券
        llUnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(AppConstant.SELECTED_COUPON, null);
            }
        });
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
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.empty_coupon, "暂时没有可用优惠券"));
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
        mPresenter.getCouponList(uuid, ApiConstants.CouponStatus.USE, buyPrice);
    }

    public int getCount() {
        return mList.size();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
