package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.commonutils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.adapter.MyBankAdapter;
import cn.zeppin.product.ntb.ui.user.contract.CommonContract;
import cn.zeppin.product.ntb.ui.user.model.CommonModel;
import cn.zeppin.product.ntb.ui.user.presenter.CommonPresenter;
import rx.functions.Action1;

/**
 * 我的银行卡
 */
public class MyBankListActivity extends BaseActivity<CommonPresenter, CommonModel> implements CommonContract.View, View.OnClickListener, BaseActivity.IRefreshListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bank_recyclerView)
    RecyclerView bankRecyclerView;
//    @Bind(R.id.tv_add)
//    TextView tvAdd;

    private MyBankAdapter mAdapter;
    private List<MyBank> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bank_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("银行卡");
        ivBack.setOnClickListener(this);
//        tvAdd.setOnClickListener(this);

        bankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyBankAdapter(mList);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footView = inflater.inflate(R.layout.my_bank_list_foot, null);
        mAdapter.setFooterView(footView);
        bankRecyclerView.setAdapter(mAdapter);

        initData();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.INTENT_BANK, mList.get(position).getUuid());
                startActivityForResult(MyBankDetailActivity.class, bundle, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddBankActivity.class, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
            }
        });

        //监听信息变化
        mRxManager.on(AppConstant.REFRESH_MY_BANK, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                String uuid = AppApplication.getInstance().getUuid();
                if (!TextUtils.isEmpty(uuid)) {
                    mList.clear();
                    mPresenter.getBindingCard(uuid);
                }
            }
        });
    }

    private void initData() {
        if (NetWorkUtils.isNetConnected(this)) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        } else {
            showToastCenter(getResources().getString(R.string.no_net));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
//            case R.id.tv_add://添加银行卡
//                startActivityForResult(AddBankActivity.class, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
//                break;
            default:
                break;
        }
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
        mAdapter.setEmptyView(netErrorlayoutResId(this));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendCodeSuccess() {

    }

    @Override
    public void sendCodeFailed(String message) {

    }

    @Override
    public void returnMyBankList(List<MyBank> list) {
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnFailed(String error) {
        mAdapter.setEmptyView(netErrorlayoutResId(this));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUserInfo(User user) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.RequestCode.REQUEST_CODE_BANKLIST) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }

    @Override
    public void toRefresh() {
        initData();
    }
}
