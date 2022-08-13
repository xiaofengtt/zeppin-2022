// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ForgotPasswordActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.ForgotPasswordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624183, "field 'etPhone'");
    target.etPhone = finder.castView(view, 2131624183, "field 'etPhone'");
    view = finder.findRequiredView(source, 2131624184, "field 'etCode'");
    target.etCode = finder.castView(view, 2131624184, "field 'etCode'");
    view = finder.findRequiredView(source, 2131624185, "field 'tvGetCode'");
    target.tvGetCode = finder.castView(view, 2131624185, "field 'tvGetCode'");
    view = finder.findRequiredView(source, 2131624186, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624186, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624095, "field 'btnNext'");
    target.btnNext = finder.castView(view, 2131624095, "field 'btnNext'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.etPhone = null;
    target.etCode = null;
    target.tvGetCode = null;
    target.tvWarn = null;
    target.btnNext = null;
  }
}
