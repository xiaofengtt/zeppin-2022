package cn.zeppin.product.ntb.ui.main.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.base.BaseFragment;
import com.geng.library.commonutils.ContextUtils;
import com.geng.library.commonutils.NetWorkUtils;
import com.geng.library.commonutils.ToastUitl;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.ui.bank.activity.BankDetailActivity;
import cn.zeppin.product.ntb.ui.bank.activity.SearchActivity;
import cn.zeppin.product.ntb.ui.bank.adapter.ProductListAdapter;
import cn.zeppin.product.ntb.ui.bank.contract.BankContract;
import cn.zeppin.product.ntb.ui.bank.model.BankModel;
import cn.zeppin.product.ntb.ui.bank.presenter.BankPresenter;
import cn.zeppin.product.ntb.widget.WrapContentLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * des:银行理财
 * Created by geng
 * on 2016.09.17:07
 */
public class BankFragment extends BaseFragment<BankPresenter, BankModel> implements BankContract.View, BaseQuickAdapter.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, BaseFragment.IRefreshListener {
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.bank_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.bank_refreshLayout)
    BGARefreshLayout mRefreshLayout;
    @Bind(R.id.bank_flowLayout)
    TagFlowLayout mBankFlowLayout;
    @Bind(R.id.term_flowLayout)
    TagFlowLayout mTermFlowLayout;
    @Bind(R.id.ll_bankFilter)
    LinearLayout mLlBankFilter;
    @Bind(R.id.cb_filter)
    CheckBox mCbFilter;
    @Bind(R.id.img_search)
    ImageView mImgSearch;
    @Bind(R.id.btn_reset)
    Button mBtnReset;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @Bind(R.id.bankBanner)
    Banner mBanner;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.bank_PtrClassicFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.bank_coordinatorLayout)
    CoordinatorLayout bankCoordinatorLayout;
    @Bind(R.id.ll_tabLayout)
    LinearLayout llTabLayout;

    private LayoutInflater mInflater;

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

    //按银行筛选
    private List<Bank> mBankList = new ArrayList<>();
    //按期限筛选
    private String[] mTermVals = new String[]{"小于60天", "61-120天", "121-180天", "181-365天", "大于365天"};

    //设置图片资源:url或本地资源
    private List<String> bannerList = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    private boolean isLoadFirst = true;

    private TagAdapter bankTagAdapter;
    private TagAdapter termTagAdapter;

    private List<Bank> bankList;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_bank;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        getActivity().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 1, 设置 addOnTabSelectedListener
        // 设置 addOnTabSelectedListener 必须在 addTab 之前。
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
                mLlBottom.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mLlBankFilter.setVisibility(View.GONE);
                reloadData(mSorts);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mSorts = AppConstant.BANK_ANNUALIZED_TYPE + "-" + (type1_isAsc ? "asc" : "desc");
                        type1_isAsc = !type1_isAsc;
                        break;
                    case 1:
                        mSorts = AppConstant.BANK_TERM_TYPE + "-" + (type2_isAsc ? "asc" : "desc");
                        type2_isAsc = !type2_isAsc;
                        break;
                    case 2:
                        mSorts = AppConstant.BANK_AMOUNT_TYPE + "-" + (type3_isAsc ? "asc" : "desc");
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
        ContextUtils.setIndicator(getActivity(), mTabLayout, 15, 15);
        //banner
        initBanner();

        final WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ProductListAdapter(mList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //刷新
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        mRefreshLayout.setDelegate(this);
        //下拉刷新不可用
        mRefreshLayout.setPullDownRefreshEnable(false);

        mCbFilter.setOnClickListener(this);
        mImgSearch.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);

        mCbFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRefreshLayout.setVisibility(View.GONE);
                    llTabLayout.setVisibility(View.GONE);
                    mLlBankFilter.setVisibility(View.VISIBLE);
                    mLlBottom.setVisibility(View.VISIBLE);
                } else {
                    mRefreshLayout.setVisibility(View.VISIBLE);
                    llTabLayout.setVisibility(View.VISIBLE);
                    mLlBankFilter.setVisibility(View.GONE);
                    mLlBottom.setVisibility(View.GONE);
                }
            }
        });

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setHeaderView(View.inflate(getContext(), R.layout.loading_header_pull_view, null));
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, bankCoordinatorLayout, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //顶部下拉刷新
                mPresenter.getBankList();
                mTerm = "all";
                mCustodian = "all";
                mStartPage = 1;
                mList.clear();
                mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
            }
        });
        //判断列表是否滑到顶部，列表滑到顶部，顶部才可以下拉刷新
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstPosition == 0) {
                    mPtrFrame.setEnabled(true);
                } else {
                    mPtrFrame.setEnabled(false);
                }
            }
        });

        mPresenter.getBankList();
    }

    private void initBanner() {
        //设置图片标题:自动对应

        List<String> titlesList = new ArrayList<>();
        titlesList.add("");
        titlesList.add("");
        titlesList.add("");
        bannerList.add(ApiConstants.RESOURCE_HOST + "/resource/product_list_banner.png");
        bannerList.add(ApiConstants.RESOURCE_HOST + "/resource/product_list_banner2.png");
        bannerList.add(ApiConstants.RESOURCE_HOST + "/resource/product_list_banner3.png");
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);

//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerTitles(titlesList);
        //设置图片加载器
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).error(R.drawable.product_head_bg).into(imageView);
            }
        });
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        mBanner.setImages(bannerList);

        mBanner.start();
    }

    /**
     * 银行
     */
    private void setBankLayout(List<Bank> bankList) {
        mBankList.clear();
        mBankList.addAll(bankList);
        if (bankTagAdapter == null) {
            bankTagAdapter = new TagAdapter<Bank>(mBankList) {
                @Override
                public View getView(FlowLayout parent, int position, Bank bank) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.layout_tag_txt,
                            mBankFlowLayout, false);
                    tv.setText(bank.getShortName());
                    return tv;
                }
            };
            //银行筛选
            mBankFlowLayout.setAdapter(bankTagAdapter);
        } else {
            bankTagAdapter.notifyDataChanged();
        }
    }

    /**
     * 产品期限
     */
    private void setTermLayout() {
        if (termTagAdapter == null) {
            termTagAdapter = new TagAdapter<String>(mTermVals) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.layout_tag_term,
                            mBankFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            };
            mTermFlowLayout.setAdapter(termTagAdapter);
        } else {
            termTagAdapter.notifyDataChanged();
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        mStartPage = 0;
//        mList.clear();
//        mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mStartPage < totalPageCount) {
            mStartPage++;
            mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
        }
        return false;
    }

    @Override
    public void returnProductList(List<Product> list, int totalPageCount) {
        stopRefresh();
        if (mStartPage == 1) {
            mList.clear();
        }
        if (list != null && list.size() > 0) {
            this.totalPageCount = totalPageCount;
            mList.addAll(list);
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.search_empty, ""));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnBankList(List<Bank> bankList) {

        setBankLayout(bankList);

        setTermLayout();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //过期产品无法查看详情
        if (mAdapter.getData().size() > 0 && mAdapter.getData().get(position).isFlagBuy()) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.PRODUCT_UUID, mAdapter.getData().get(position).getUuid());
            startActivity(BankDetailActivity.class, bundle);
        }
    }

    @Override
    public void showLoading(String title) {
        stopProgressDialog();
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
        stopRefresh();
    }

    @Override
    public void showErrorTip(String msg) {
        stopRefresh();
        netError();
        ToastUitl.showToastCenter(msg);
    }

    private void stopRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        }
        mPtrFrame.refreshComplete();
    }

    private void reloadData(String sorts) {
        mList.clear();
        mStartPage = 1;
        mPresenter.getProductList("", mCustodian, mTerm, sorts, mStartPage, ApiConstants.PAGESIZE);
    }

    private void netError() {
        if (mAdapter != null) {
            mAdapter.setEmptyView(emptylayoutResId(R.drawable.ic_net_off, getResources().getString(R.string.net_off), this));
            mAdapter.notifyDataSetChanged();
        } else {
//            showNetErrorTip();
            ToastUitl.showToastCenter(getText(R.string.no_net).toString());
        }
    }

    @Override
    public void onClick(View v) {
        Iterator<Integer> bankIterator = mBankFlowLayout.getSelectedList().iterator();
        Iterator<Integer> termIterator = mTermFlowLayout.getSelectedList().iterator();
        switch (v.getId()) {
            case R.id.img_search://搜索
                startActivity(SearchActivity.class);
                break;
            case R.id.btn_reset://取消
                mRefreshLayout.setVisibility(View.VISIBLE);
                llTabLayout.setVisibility(View.VISIBLE);
                mLlBankFilter.setVisibility(View.GONE);
                mLlBottom.setVisibility(View.GONE);
                mCbFilter.setChecked(false);

                mBankFlowLayout.setMaxSelectCount(0);
                mTermFlowLayout.setMaxSelectCount(0);
                while (bankIterator.hasNext()) {
                    TagView pre = (TagView) mBankFlowLayout.getChildAt(bankIterator.next());
                    pre.setChecked(false);
                }
//                Iterator<Integer> termIterator = mTermFlowLayout.getSelectedList().iterator();
                while (termIterator.hasNext()) {
                    TagView pre = (TagView) mTermFlowLayout.getChildAt(termIterator.next());
                    pre.setChecked(false);
                }
                mTerm = "all";
                mCustodian = "all";
                mList.clear();
                mStartPage = 1;
                mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
                break;
            case R.id.btn_submit://提交
                String term = "";
                String custodian = "";
//                Iterator<Integer> bankIterator = mBankFlowLayout.getSelectedList().iterator();
                while (bankIterator.hasNext()) {
                    custodian += mBankList.get(bankIterator.next()).getUuid() + ",";
                }
                while (termIterator.hasNext()) {
                    term += termIterator.next() + 1 + ",";
                }
                if (!"".equals(term)) {
                    mTerm = term.substring(0, term.length() - 1);
                } else {
                    mTerm = "all";
                }
                if (!"".equals(custodian)) {
                    mCustodian = custodian.substring(0, custodian.length() - 1);
                } else {
                    mCustodian = "all";
                }
                if (!"".equals(term) || !"".equals(custodian)) {
                    mCbFilter.setChecked(true);
                } else {
                    mCbFilter.setChecked(false);
                }
//                mCustodian = mCustodian.substring(0, mCustodian.length() - 1);
                mList.clear();
                mStartPage = 1;
                mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);

                mRefreshLayout.setVisibility(View.VISIBLE);
                llTabLayout.setVisibility(View.VISIBLE);
                mLlBankFilter.setVisibility(View.GONE);
                mLlBottom.setVisibility(View.GONE);
                mCbFilter.setChecked(false);
                break;
        }
    }


    private void setupTabIcons() {
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(1)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(2)));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
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

    @Override
    public void toRefresh() {
        if (NetWorkUtils.isNetConnected(getContext())) {
            mStartPage = 1;
            mList.clear();
            mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
        } else {
            netError();
        }
    }

    @Override
    public void onResume() {
        //每次显示都重新加载
        super.onResume();
        if (!isLoadFirst) {
            stopProgressDialog();
            //切换时不刷新银行列表，只有在第一次进入或者下拉刷新才获取
//            mPresenter.getBankList();
            mList.clear();
            mStartPage = 1;
            mPresenter.getProductList("", mCustodian, mTerm, mSorts, mStartPage, ApiConstants.PAGESIZE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        isLoadFirst = false;
    }
}
