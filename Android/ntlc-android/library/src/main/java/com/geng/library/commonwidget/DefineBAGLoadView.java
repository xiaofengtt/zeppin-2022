
package com.geng.library.commonwidget;

/**
 * Created by fml on 2015/12/3 0003.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.R;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by fml on 2015/10/9 0009.
 * 创建时间:15/10/9 13:16
 * 描述:慕课网下拉刷新风格
 */
public class DefineBAGLoadView extends BGARefreshViewHolder {
    /**
     * 原始的图片
     */
    private Bitmap mOriginalBitmap;
    /**
     * 最终生成图片的填充颜色
     */
    private int mUltimateColor = -1;
    private boolean isRefreshEnabled = true;
    /**
     * 是否使用上拉加载
     */
    private boolean mIsLoadingMoreEnabled = true;

    public DefineBAGLoadView(Context context, boolean isLoadingMoreEnabled, boolean isRefreshEnabled) {
        super(context, isLoadingMoreEnabled);
        this.mIsLoadingMoreEnabled = isLoadingMoreEnabled;
        this.isRefreshEnabled = isRefreshEnabled;
    }

    /**
     * 定义刷新
     */
    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
        }
        if (!isRefreshEnabled) {
            return null;
        }
        return this.mRefreshHeaderView;
    }

    /**
     * 设置原始的图片
     *
     * @param originalBitmap
     */
    public void setOriginalBitmap(Bitmap originalBitmap) {
        mOriginalBitmap = originalBitmap;
    }

    /**
     * 设置最终生成图片的填充颜色
     *
     * @param ultimateColor
     */
    public void setUltimateColor(int ultimateColor) {
        mUltimateColor = ultimateColor;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }


    @Override
    public void changeToRefreshing() {
    }

    @Override
    public void onEndRefreshing() {
    }

    /**
     * 设置加载
     */
    public void updateLoadingMoreText(String text) {
        this.mFooterStatusTv.setText(text);
    }

    /**
     * 隐藏加载更多图片
     */
    public void hideLoadingMoreImg() {
        this.mFooterChrysanthemumIv.setVisibility(View.GONE);
    }

    /**
     * 显示加载更多图片
     */
    public void showLoadingMoreImg() {
        this.mFooterChrysanthemumIv.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义加载更多底部
     */
    @Override
    public View getLoadMoreFooterView() {
        if (!this.mIsLoadingMoreEnabled) {
            return null;
        }
        if (this.mLoadMoreFooterView == null) {
            this.mLoadMoreFooterView = View.inflate(this.mContext, R.layout.view_normal_refresh_footer, null);
            this.mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            this.mFooterStatusTv = (TextView) this.mLoadMoreFooterView.findViewById(R.id.tv_normal_refresh_footer_status);
            this.mFooterChrysanthemumIv = (ImageView) this.mLoadMoreFooterView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            this.mFooterChrysanthemumAd = (AnimationDrawable) this.mFooterChrysanthemumIv.getDrawable();
            this.mFooterStatusTv.setText(this.mLodingMoreText);
        }
        return mLoadMoreFooterView;
    }
}