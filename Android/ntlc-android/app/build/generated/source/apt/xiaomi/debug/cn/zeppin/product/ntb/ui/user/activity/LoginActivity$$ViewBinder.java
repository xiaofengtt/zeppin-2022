// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624201, "field 'mTabLayout'");
    target.mTabLayout = finder.castView(view, 2131624201, "field 'mTabLayout'");
    view = finder.findRequiredView(source, 2131624202, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131624202, "field 'viewPager'");
    view = finder.findRequiredView(source, 2131624199, "field 'llLoginView'");
    target.llLoginView = finder.castView(view, 2131624199, "field 'llLoginView'");
    view = finder.findRequiredView(source, 2131624200, "field 'ivLog'");
    target.ivLog = finder.castView(view, 2131624200, "field 'ivLog'");
  }

  @Override public void unbind(T target) {
    target.mTabLayout = null;
    target.viewPager = null;
    target.llLoginView = null;
    target.ivLog = null;
  }
}
