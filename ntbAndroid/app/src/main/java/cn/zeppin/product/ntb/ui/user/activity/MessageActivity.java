package cn.zeppin.product.ntb.ui.user.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.commonutils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Message;
import cn.zeppin.product.ntb.ui.user.adapter.MessageAdapter;
import cn.zeppin.product.ntb.ui.user.contract.MessageContract;
import cn.zeppin.product.ntb.ui.user.model.MessageModel;
import cn.zeppin.product.ntb.ui.user.presenter.MessagePresenter;

import static com.umeng.socialize.utils.ContextUtil.getContext;

public class MessageActivity extends BaseActivity<MessagePresenter, MessageModel> implements MessageContract.View, BaseActivity.IRefreshListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.message_recyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.message_refreshLayout)
    BGARefreshLayout mRefreshLayout;

    private MessageAdapter mAdapter;
    private List<Message> mList = new ArrayList<>();
    private String uuid = AppApplication.getInstance().getUuid();
    private int mStartPage = 1;
    private int totalPageCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("我的消息");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setText("全部标为已读");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全部标为已读
                mPresenter.setAllRead(uuid);
            }
        });

        mAdapter = new MessageAdapter(mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mList.get(position).setEllipsize(false);
                mAdapter.notifyItemChanged(position);
                mPresenter.setRead(mList.get(position).getUuid(), uuid);
            }
        });
        messageRecyclerView.setAdapter(mAdapter);

        //刷新
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        mRefreshLayout.setDelegate(this);
        startProgressDialog();
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
        mAdapter.setEmptyView(netErrorlayoutResId(this));
        mAdapter.notifyDataSetChanged();
        stopRefresh();
    }

    @Override
    public void returnMessageList(List<Message> list, int totalPageCount) {
        stopRefresh();
        this.totalPageCount = totalPageCount;
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId("暂时没有任何消息", this));
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnReadStatus(boolean isRead) {

    }

    @Override
    public void returnAllReadStatus(boolean isRead) {
        if (isRead) {//全部标为已读成功
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setFlagRead(true);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void toRefresh() {
        reLoad();
    }

    private void reLoad() {
        startProgressDialog();
        if (NetWorkUtils.isNetConnected(getContext())) {
            mStartPage = 1;
            mList.clear();
            loadData();
        } else {
            mAdapter.setEmptyView(netErrorlayoutResId(this));
            mAdapter.notifyDataSetChanged();
            stopLoading();
            stopRefresh();
        }
    }

    private void loadData() {
        mPresenter.getMessageList(uuid, mStartPage, ApiConstants.PAGESIZE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        reLoad();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mStartPage < totalPageCount) {
            mStartPage++;
            loadData();
        }
        return false;
    }

    private void stopRefresh() {
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }
}
