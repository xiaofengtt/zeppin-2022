// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AccountFragment$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.fragment.AccountFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624183, "field 'etPhone'");
    target.etPhone = finder.castView(view, 2131624183, "field 'etPhone'");
    view = finder.findRequiredView(source, 2131624239, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131624239, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131624309, "field 'btnLogin'");
    target.btnLogin = finder.castView(view, 2131624309, "field 'btnLogin'");
    view = finder.findRequiredView(source, 2131624310, "field 'tvForgetPwd'");
    target.tvForgetPwd = finder.castView(view, 2131624310, "field 'tvForgetPwd'");
    view = finder.findRequiredView(source, 2131624186, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624186, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624224, "field 'tvAgreement'");
    target.tvAgreement = finder.castView(view, 2131624224, "field 'tvAgreement'");
  }

  @Override public void unbind(T target) {
    target.etPhone = null;
    target.etPassword = null;
    target.btnLogin = null;
    target.tvForgetPwd = null;
    target.tvWarn = null;
    target.tvAgreement = null;
  }
}
