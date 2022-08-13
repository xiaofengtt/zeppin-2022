package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;
import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.TradingRecord;
import cn.zeppin.product.ntb.bean.TradingRecordList;
import cn.zeppin.product.ntb.ui.user.adapter.TradingRecordAdapter;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BILL_ID;

/**
 * 交易记录
 */
public class TradingRecordActivity extends BaseActivity implements BaseActivity.IRefreshListener {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tRecord_recyclerView)
    RecyclerView tRecordRecyclerView;

    private TradingRecordAdapter mAdapter;
    private List<TradingRecord> mList = new ArrayList<>();
    private List<TradingRecordList> mTradingRecordList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_trading_record;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("交易记录");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (mList.size() > position) {
                            return mList.get(position).getCreatetimeCN().substring(0, 8);
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (mList.size() > position) {
                            LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            TextView textView = (TextView) mInflater.inflate(R.layout.item_trading_record_head, tRecordRecyclerView, false);

                            textView.setText(mList.get(position).getCreatetimeCN().substring(0, 8));
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                            layoutParams.setMargins(0, ContextUtils.dip2px(TradingRecordActivity.this, 10), 0, ContextUtils.dip2px(TradingRecordActivity.this, 10));//4个参数按顺序分别是左上右下
//
//                            textView.setLayoutParams(layoutParams);
                            return textView;
                        } else {
                            return null;
                        }
                    }
                })
                .setGroupHeight((int) getResources().getDimension(R.dimen.y180))       //设置高度
                .setGroupBackground(getResources().getColor(R.color.color_EFEFEF))
                .build();


        tRecordRecyclerView.addItemDecoration(decoration);
        mAdapter = new TradingRecordAdapter(mList);
        tRecordRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_BILL_ID, mList.get(position).getUuid());
                startActivity(TradingRecordDetailActivity.class, bundle);
            }
        });
        initData();

    }


    private void returnTradingRecordsList(List<TradingRecordList> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                mList.addAll(list.get(i).getDataList());
            }
        }
        if (mList.size() == 0) {
            mAdapter.setEmptyView(emptylayoutResId(this));
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showNetErrorTip(String error) {
        ToastUitl.showToastCenter(error);
        if (mAdapter != null) {
            mAdapter.setEmptyView(netErrorlayoutResId(this));
            mAdapter.notifyDataSetChanged();
        } else {
            showNetErrorTip();
        }
    }

    private void initData() {
        startProgressDialog("加载中...");
        final String uuid = AppApplication.getInstance().getUuid();
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(getTradingRecordList(uuid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData<TradingRecordList>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData<TradingRecordList> resultData) {
                                    if (resultData.success()) {
                                        returnTradingRecordsList(resultData.getData());
                                    } else {
                                        showNetErrorTip(resultData.getMessage());
                                    }
                                    stopProgressDialog();
                                }

                                @Override
                                protected void _onError(String message) {
                                    stopProgressDialog();
                                    showNetErrorTip(message);
                                }
                            }));

                } else {
                    stopProgressDialog();
                    showNetErrorTip(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                stopProgressDialog();
                showNetErrorTip(message);
            }
        }));
    }

    private Observable<ResultData<TradingRecordList>> getTradingRecordList(String uuid, String token) {
        return Api.getDefault().getTradingRecordList(uuid, token)
                .map(new Func1<ResultData<TradingRecordList>, ResultData<TradingRecordList>>() {
                    @Override
                    public ResultData<TradingRecordList> call(ResultData<TradingRecordList> tradingRecordListResultData) {
                        return tradingRecordListResultData;
                    }
                }).compose(RxSchedulers.<ResultData<TradingRecordList>>io_main());
    }

    @Override
    public void toRefresh() {
        initData();
    }
}
