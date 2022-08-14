package cn.zeppin.product.ntb.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.ui.finance.adapter.TabPagerAdapter;
import cn.zeppin.product.ntb.ui.user.fragment.CouponAvailableFragment;
import cn.zeppin.product.ntb.ui.user.fragment.CouponHistoryFragment;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.AppConstant.COUPON_USE_COUNT;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BUY_PRICE;
import static cn.zeppin.product.ntb.app.AppConstant.SELECTED_COUPON;

/**
 * 优惠券
 */
public class CouponActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.coupon_tabLayout)
    SlidingTabLayout couponTabLayout;
    @Bind(R.id.coupon_viewPager)
    ViewPager couponViewPager;

    private List<Fragment> mFragment;
    private String[] titles = {"可用券", "历史券"};
    private TabPagerAdapter mTabPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        tvTitle.setText("优惠券");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final CouponAvailableFragment couponAvailableFragment;
        //购买的金额
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String buyPrice = bundle.getString(INTENT_BUY_PRICE);
            Coupon currentCoupon = (Coupon) bundle.getSerializable(SELECTED_COUPON);

            couponTabLayout.setVisibility(View.GONE);
            //购买时只显示可用的优惠券
            mFragment = new ArrayList<>();
            mFragment.add(couponAvailableFragment = new CouponAvailableFragment().newInstance(buyPrice, currentCoupon));
            mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragment, new String[]{"可用券"});
            couponViewPager.setAdapter(mTabPagerAdapter);
            couponTabLayout.setViewPager(couponViewPager);
        } else {
            couponTabLayout.setVisibility(View.VISIBLE);
            mFragment = new ArrayList<>();
            mFragment.add(couponAvailableFragment = new CouponAvailableFragment());
            mFragment.add(new CouponHistoryFragment());
            mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragment, titles);
            couponViewPager.setAdapter(mTabPagerAdapter);
            couponTabLayout.setViewPager(couponViewPager);

        }

        mRxManager.on(AppConstant.SELECTED_COUPON, new Action1<Coupon>() {
            @Override
            public void call(Coupon coupon) {
                Intent intent = new Intent();
                intent.putExtra(SELECTED_COUPON, coupon);
                intent.putExtra(COUPON_USE_COUNT, couponAvailableFragment.getCount());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
