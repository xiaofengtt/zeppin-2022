// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CouponActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.CouponActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624159, "field 'couponTabLayout'");
    target.couponTabLayout = finder.castView(view, 2131624159, "field 'couponTabLayout'");
    view = finder.findRequiredView(source, 2131624160, "field 'couponViewPager'");
    target.couponViewPager = finder.castView(view, 2131624160, "field 'couponViewPager'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.couponTabLayout = null;
    target.couponViewPager = null;
  }
}
