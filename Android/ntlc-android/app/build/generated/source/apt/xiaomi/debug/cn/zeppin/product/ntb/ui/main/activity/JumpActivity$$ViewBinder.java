// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class JumpActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.JumpActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624198, "field 'ivJump'");
    target.ivJump = finder.castView(view, 2131624198, "field 'ivJump'");
  }

  @Override public void unbind(T target) {
    target.ivJump = null;
  }
}
