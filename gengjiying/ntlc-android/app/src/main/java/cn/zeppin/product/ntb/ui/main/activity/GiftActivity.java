package cn.zeppin.product.ntb.ui.main.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.ToastUitl;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.ui.main.adapter.GiftAdapter;
import cn.zeppin.product.ntb.ui.main.contract.GiftContract;
import cn.zeppin.product.ntb.ui.main.model.GiftModel;
import cn.zeppin.product.ntb.ui.main.presenter.GiftPresenter;
import cn.zeppin.product.ntb.utils.ImageUtil;
import cn.zeppin.product.ntb.utils.ShareUtil;
import cn.zeppin.product.ntb.widget.WrapContentLinearLayoutManager;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_GIFT;
import static cn.zeppin.product.ntb.utils.ShareUtil.chinesizationByPlatform;

/**
 * 描述：礼包
 * 开发人: geng
 * 创建时间: 17/12/12
 */

public class GiftActivity extends BaseActivity<GiftPresenter, GiftModel> implements GiftContract.View, View.OnClickListener {
    @Bind(R.id.gift_recyclerView)
    RecyclerView giftRecyclerView;
    @Bind(R.id.iv_bg_gift)
    ImageView ivBgGift;
    @Bind(R.id.tv_receive)
    TextView tvReceive;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;

    private GiftAdapter mAdapter;
    private List<Coupon> mList = new ArrayList<>();
    private Gift data = new Gift();
    private String uuid = AppApplication.getInstance().getUuid();
    //优惠券数组
    private String coupons = "";
    //现金红包数组
    private String redPackets = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_gift;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        giftRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GiftAdapter(mList);
        giftRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (Gift) bundle.getSerializable(INTENT_GIFT);
            returnNotActiveList(data);
            if (data != null && data.getRedPacket().size() > 0) {
                //如果有现金红包就提示分享，否则只显示立即领取
                setShareView(false);
            }
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
        showToastCenter(msg);
    }

    @Override
    public void returnNotActiveList(Gift gift) {
        coupons = "";
        redPackets = "";
        if (gift != null) {
            data = gift;
            mList.clear();
            if (gift.getRedPacket().size() > 0) {
                mList.addAll(gift.getRedPacket());
                //现金红包uuid的数组
                for (int i = 0; i < gift.getRedPacket().size(); i++) {
                    redPackets += gift.getRedPacket().get(i).getUuid() + ",";
                }
                redPackets = redPackets.substring(0, redPackets.length() - 1);

                setShareView(false);
            } else {
                setShareView(true);
            }
            if (gift.getCoupon().size() > 0) {
                mList.addAll(gift.getCoupon());
                //获取优惠券的uuid的数组
                for (int i = 0; i < gift.getCoupon().size(); i++) {
                    coupons += gift.getCoupon().get(i).getUuid() + ",";
                }
                coupons = coupons.substring(0, coupons.length() - 1);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void receiveActivateSuccess(boolean flagShare) {
        //大礼包领取成功
        finish();
    }

    @OnClick({R.id.tv_receive, R.id.tv_share, R.id.tv_cancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_receive://立即领取
                mPresenter.receiveActivate(uuid, coupons, true, redPackets);
                break;
            case R.id.tv_share://分享
                if (data.getRedPacket() != null && data.getRedPacket().size() > 0) {
                    String text = CollectionUtils.doubleToString(data.getRedPacket().get(0).getCouponPrice() + 2);
                    Bitmap bmp = ImageUtil.drawTextToBitmap(this, R.drawable.gift_act_bg, text);
                    new ShareUtil(this).shareStyleImage(SHARE_MEDIA.WEIXIN_CIRCLE, bmp, "理财产品", new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            startProgressDialog();
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            stopProgressDialog();
                            mAdapter.setShareSuccess(true);
                            mAdapter.notifyDataSetChanged();
                            setShareView(true);
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            stopProgressDialog();
                            if (UMShareAPI.get(GiftActivity.this).isInstall(GiftActivity.this, share_media)) {
                                ToastUitl.showToastCenter(throwable.getMessage());
                            } else {
                                ToastUitl.showToastCenter(chinesizationByPlatform(share_media) + "应用未安装");
                            }
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            stopProgressDialog();
                            showToastCenter("已取消分享");
                        }
                    });
                }
                break;
            case R.id.tv_cancel://不分享，直接领取
                mPresenter.receiveActivate(uuid, coupons, false, redPackets);
//                new ShareUtil(this).shareMenu("https://work.bugtags.com/apps/1568423609901094","理财产品",null);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    private void setShareView(boolean isShowOne) {
        if (isShowOne) {
            tvShare.setVisibility(View.GONE);
            tvCancel.setVisibility(View.GONE);
            tvReceive.setVisibility(View.VISIBLE);
            ivBgGift.setImageDrawable(getResources().getDrawable(R.drawable.gift_share_after));
        } else {
            tvShare.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.VISIBLE);
            tvReceive.setVisibility(View.GONE);
            ivBgGift.setImageDrawable(getResources().getDrawable(R.drawable.gift_share_before));
        }
    }
}
