// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SplashActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.SplashActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624254, "field 'llSplash'");
    target.llSplash = finder.castView(view, 2131624254, "field 'llSplash'");
  }

  @Override public void unbind(T target) {
    target.llSplash = null;
  }
}
