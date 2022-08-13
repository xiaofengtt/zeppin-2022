// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CreateGestureActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.CreateGestureActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624161, "field 'lockPatternIndicator'");
    target.lockPatternIndicator = finder.castView(view, 2131624161, "field 'lockPatternIndicator'");
    view = finder.findRequiredView(source, 2131624162, "field 'messageTv'");
    target.messageTv = finder.castView(view, 2131624162, "field 'messageTv'");
    view = finder.findRequiredView(source, 2131624163, "field 'lockPatternView'");
    target.lockPatternView = finder.castView(view, 2131624163, "field 'lockPatternView'");
    view = finder.findRequiredView(source, 2131624164, "field 'resetBtn'");
    target.resetBtn = finder.castView(view, 2131624164, "field 'resetBtn'");
    view = finder.findRequiredView(source, 2131624166, "field 'skipBtn'");
    target.skipBtn = finder.castView(view, 2131624166, "field 'skipBtn'");
    view = finder.findRequiredView(source, 2131624165, "field 'viewLine'");
    target.viewLine = view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
  }

  @Override public void unbind(T target) {
    target.lockPatternIndicator = null;
    target.messageTv = null;
    target.lockPatternView = null;
    target.resetBtn = null;
    target.skipBtn = null;
    target.viewLine = null;
    target.ivBack = null;
    target.tvTitle = null;
  }
}
