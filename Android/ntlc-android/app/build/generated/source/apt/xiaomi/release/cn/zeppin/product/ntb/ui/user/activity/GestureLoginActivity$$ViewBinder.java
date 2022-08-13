// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GestureLoginActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.GestureLoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624163, "field 'lockPatternView'");
    target.lockPatternView = finder.castView(view, 2131624163, "field 'lockPatternView'");
    view = finder.findRequiredView(source, 2131624188, "field 'messageTv'");
    target.messageTv = finder.castView(view, 2131624188, "field 'messageTv'");
    view = finder.findRequiredView(source, 2131624191, "field 'forgetGestureBtn' and method 'forgetGesturePasswrod'");
    target.forgetGestureBtn = finder.castView(view, 2131624191, "field 'forgetGestureBtn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.forgetGesturePasswrod();
        }
      });
    view = finder.findRequiredView(source, 2131624187, "field 'ivHead'");
    target.ivHead = finder.castView(view, 2131624187, "field 'ivHead'");
    view = finder.findRequiredView(source, 2131624189, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624189, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624192, "field 'btnOtherAccount' and method 'otherAccount'");
    target.btnOtherAccount = finder.castView(view, 2131624192, "field 'btnOtherAccount'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.otherAccount();
        }
      });
    view = finder.findRequiredView(source, 2131624190, "field 'llBottom'");
    target.llBottom = finder.castView(view, 2131624190, "field 'llBottom'");
  }

  @Override public void unbind(T target) {
    target.lockPatternView = null;
    target.messageTv = null;
    target.forgetGestureBtn = null;
    target.ivHead = null;
    target.tvWarn = null;
    target.btnOtherAccount = null;
    target.llBottom = null;
  }
}
