// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SafeActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.SafeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624246, "field 'rlEditPwd'");
    target.rlEditPwd = finder.castView(view, 2131624246, "field 'rlEditPwd'");
    view = finder.findRequiredView(source, 2131624245, "field 'rlSetPwd'");
    target.rlSetPwd = finder.castView(view, 2131624245, "field 'rlSetPwd'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.rlEditPwd = null;
    target.rlSetPwd = null;
  }
}
