// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhoneFragment$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.fragment.PhoneFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624183, "field 'etPhone'");
    target.etPhone = finder.castView(view, 2131624183, "field 'etPhone'");
    view = finder.findRequiredView(source, 2131624184, "field 'etCode'");
    target.etCode = finder.castView(view, 2131624184, "field 'etCode'");
    view = finder.findRequiredView(source, 2131624309, "field 'btnLogin'");
    target.btnLogin = finder.castView(view, 2131624309, "field 'btnLogin'");
    view = finder.findRequiredView(source, 2131624185, "field 'tvGetCode'");
    target.tvGetCode = finder.castView(view, 2131624185, "field 'tvGetCode'");
    view = finder.findRequiredView(source, 2131624186, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624186, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624224, "field 'tvAgreement'");
    target.tvAgreement = finder.castView(view, 2131624224, "field 'tvAgreement'");
  }

  @Override public void unbind(T target) {
    target.etPhone = null;
    target.etCode = null;
    target.btnLogin = null;
    target.tvGetCode = null;
    target.tvWarn = null;
    target.tvAgreement = null;
  }
}
