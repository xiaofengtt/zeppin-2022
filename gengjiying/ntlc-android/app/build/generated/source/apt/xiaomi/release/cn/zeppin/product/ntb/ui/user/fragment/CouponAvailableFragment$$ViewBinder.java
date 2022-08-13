// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CouponAvailableFragment$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.fragment.CouponAvailableFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624324, "field 'couponRecyclerView'");
    target.couponRecyclerView = finder.castView(view, 2131624324, "field 'couponRecyclerView'");
    view = finder.findRequiredView(source, 2131624325, "field 'llUnUse'");
    target.llUnUse = finder.castView(view, 2131624325, "field 'llUnUse'");
  }

  @Override public void unbind(T target) {
    target.couponRecyclerView = null;
    target.llUnUse = null;
  }
}
