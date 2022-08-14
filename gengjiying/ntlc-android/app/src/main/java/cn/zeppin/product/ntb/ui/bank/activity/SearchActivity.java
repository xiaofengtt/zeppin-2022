package cn.zeppin.product.ntb.ui.bank.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.ContextUtils;
import com.geng.library.commonutils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.ui.bank.adapter.ProductListAdapter;
import cn.zeppin.product.ntb.ui.bank.contract.BankContract;
import cn.zeppin.product.ntb.ui.bank.model.BankModel;
import cn.zeppin.product.ntb.ui.bank.presenter.BankPresenter;
import cn.zeppin.product.ntb.widget.ClearEditText;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity<BankPresenter, BankModel> implements BankContract.View, BaseQuickAdapter.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cet_search)
    ClearEditText cEtSearch;
    @Bind(R.id.id_tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.search_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_refreshLayout)
    BGARefreshLayout mRefreshLayout;


    private ProductListAdapter mAdapter;
    private List<Product> mList = new ArrayList<>();
    private int mStartPage = 1;
    private int totalPageCount = 1;
    private String mSorts;//排序
    private String mTerm = "all";//期限取值
    private String mCustodian = "all";//银行UUID

    private boolean type1_isAsc = true;
    private boolean type2_isAsc = true;
    private boolean type3_isAsc = true;


    private List<String> titles = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("搜索产品");
        ivBack.setOnClickListener(this);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mSorts = AppConstant.BANK_ANNUALIZED_TYPE + "-desc";
                        break;
                    case 1:
                        mSorts = AppConstant.BANK_TERM_TYPE + "-desc";
                        break;
                    case 2:
                        mSorts = AppConstant.BANK_AMOUNT_TYPE + "-desc";
                        break;
                }
//                changeTabDown(tab);
                reloadData(mSorts);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                unSelectedTab(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mSorts = AppConstant.BANK_ANNUALIZED_TYPE + "-" + (type1_isAsc ? "asc" : "desc");
//                        if (type1_isAsc) {
//                            changeTabUp(tab);
//                        } else {
//                            changeTabDown(tab);
//                        }
                        type1_isAsc = !type1_isAsc;
                        break;
                    case 1:
                        mSorts = AppConstant.BANK_TERM_TYPE + "-" + (type2_isAsc ? "asc" : "desc");
//                        if (type2_isAsc) {
//                            changeTabUp(tab);
//                        } else {
//                            changeTabDown(tab);
//                        }
                        type2_isAsc = !type2_isAsc;
                        break;
                    case 2:
                        mSorts = AppConstant.BANK_AMOUNT_TYPE + "-" + (type3_isAsc ? "asc" : "desc");
//                        if (type3_isAsc) {
//                            changeTabUp(tab);
//                        } else {
//                            changeTabDown(tab);
//                        }
                        type3_isAsc = !type3_isAsc;
                        break;
                }

                reloadData(mSorts);
            }
        });

        // 2.添加Tab
        titles.add("年化收益");
        titles.add("产品期限");
        titles.add("起购金额");
        setupTabIcons();
        ContextUtils.setIndicator(this, mTabLayout, 15, 15);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProductListAdapter(mList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        //刷新
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        mRefreshLayout.setDelegate(this);

        cEtSearch.setOnEditorActionListener(onEditorActionListener);

        startProgressDialog();
//        mPresenter.getBankList();

    }

    private void reloadData(String sorts) {
        mList.clear();
        mStartPage = 1;
        mPresenter.getProductList(cEtSearch.getText().toString(), mCustodian, mTerm, sorts, mStartPage, ApiConstants.PAGESIZE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (NetWorkUtils.isNetConnected(this)) {
            reloadData(mSorts);
        } else {
            netError();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (NetWorkUtils.isNetConnected(this)) {
            if (mStartPage < totalPageCount) {
                mPresenter.getProductList(cEtSearch.getText().toString(), mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
            }
        } else {
            netError();
        }
        return false;
    }

    @Override
    public void returnProductList(List<Product> list, int totalPageCount) {
        stopProgressDialog();
        stopRefresh();
        if (list != null && list.size() > 0) {
            this.totalPageCount = totalPageCount;
            mList.addAll(list);
            mStartPage++;
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.search_empty, ""));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnBankList(List<Bank> bankList) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getData().get(position).isFlagBuy()) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.PRODUCT_UUID, mAdapter.getData().get(position).getUuid());
            startActivity(BankDetailActivity.class, bundle);
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        stopProgressDialog();
        stopRefresh();
        netError();
    }


    private void stopRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        }
    }


    /**
     * 监听键盘搜索键
     */
    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                CollectionUtils.closeKeyboard(SearchActivity.this);
                reloadData(mSorts);
                return true;
            }
            return false;
        }
    };

    private void setupTabIcons() {
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(1)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(2)));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tv_tab_title);
        txt_title.setText(titles.get(position));
        if (position == 0) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_rate);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txt_title.setCompoundDrawables(drawable, null, null, null);
        }
        if (position == 1) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_term);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txt_title.setCompoundDrawables(drawable, null, null, null);
        }
        if (position == 2) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_amount);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txt_title.setCompoundDrawables(drawable, null, null, null);
        }
        return txt_title;
    }


    private void netError() {
        if (mAdapter != null) {
            mAdapter.setEmptyView(netErrorlayoutResId(new IRefreshListener() {
                @Override
                public void toRefresh() {
                    reloadData(mSorts);
                }
            }));
            mAdapter.notifyDataSetChanged();
        } else {
            showToastCenter(getResources().getString(R.string.no_net));
        }
    }

}
